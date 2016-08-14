package br.com.cafebinario.register.datamemory;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

public final class CafebinarioMemory implements Closeable {

	private final static class Singlegon {
		private final static CafebinarioMemory CAFEBINARIO_MEMORY(Map<String, SecureMemoryData> memory){
			return new CafebinarioMemory(memory);
		}
	}

	private final class VerifierExpireTokens implements Runnable, Closeable {

		private volatile boolean running;
		private Map<String, SecureMemoryData> memory;
		
		private VerifierExpireTokens(Map<String, SecureMemoryData> memory){
			this.memory = memory;
		}
		
		@Override
		public void run() {
			running = true;
			while (running) {
				try {
					if (!memory.isEmpty()
							&& memory.entrySet().iterator().hasNext())
						memory.entrySet().iterator().next().getValue().verify();
				} catch (Exception e) {
					memory.entrySet().iterator().remove();
				}

				try {
					TimeUnit.MINUTES.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void close() throws IOException {
			this.running = false;
		}
	}

	public static CafebinarioMemory getInstance(Map<String, SecureMemoryData> memory) {
		return Singlegon.CAFEBINARIO_MEMORY(memory);
	}

	private CafebinarioMemory(Map<String, SecureMemoryData> memory) {
		this.memory = memory;
		this.verifierExpireTokens = new VerifierExpireTokens(memory);
		new Thread(new VerifierExpireTokens(memory)).start();
	}

	private final VerifierExpireTokens verifierExpireTokens;
	private final Map<String, SecureMemoryData> memory;

	@Override
	public void close() throws IOException {
		this.verifierExpireTokens.close();
	}
	
	public int size() {
		return memory.size();
	}

	public boolean isEmpty() {
		return memory.isEmpty();
	}

	public boolean containsKey(final String key) {
		return memory.containsKey(key);
	}

	public UserAuthenticationVO get(final String key) {
		final SecureMemoryData secureMemoryData = memory.get(key);

		Assert.notNull(secureMemoryData);
		CipherInputStream cipherInputStream = null;
		try {
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secureMemoryData.getPair().getSecond());

			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(key.getBytes());
			cipherInputStream = new CipherInputStream(byteArrayInputStream, cipher);
			byte[] buffer = new byte[1];
			StringBuffer stringBuffer = new StringBuffer();

			while (cipherInputStream.read(buffer) > -1) {
				stringBuffer.append(new String(buffer));
			}

			String[] pipeDelimiterObjectString = new String(Base64Utils.decodeFromString(stringBuffer.toString()))
					.split("[|]");
			int i = 0;

			final UserAuthenticationVO userAuthenticationVO = new UserAuthenticationVO();
			userAuthenticationVO.setDomain(pipeDelimiterObjectString[i++]);
			userAuthenticationVO.setEmail(pipeDelimiterObjectString[i++]);
			userAuthenticationVO.setNick(pipeDelimiterObjectString[i++]);
			userAuthenticationVO.setPassword(pipeDelimiterObjectString[i++]);

			secureMemoryData.verify();
			Assert.isTrue(userAuthenticationVO.equals(secureMemoryData.getUserAuthenticationVO()));

			return secureMemoryData.getUserAuthenticationVO();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (cipherInputStream != null)
				try {
					cipherInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public SecureMemoryData put(SecureMemoryData secureMemoryData) {
		return memory.put(secureMemoryData.getPair().getFirst(), secureMemoryData);
	}

	public Set<String> keySet() {
		return memory.keySet();
	}
}