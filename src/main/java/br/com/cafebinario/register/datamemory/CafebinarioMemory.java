package br.com.cafebinario.register.datamemory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.jni.Time;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;

import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

public final class CafebinarioMemory {

	private final static class Singlegon {
		private final static CafebinarioMemory CAFEBINARIO_MEMORY = new CafebinarioMemory();
	}

	private final class VerifierExpireTokens implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					if (!Singlegon.CAFEBINARIO_MEMORY.memory.isEmpty()
							&& Singlegon.CAFEBINARIO_MEMORY.memory.entrySet().iterator().hasNext())
						Singlegon.CAFEBINARIO_MEMORY.memory.entrySet().iterator().next().getValue().verify();
				} catch (Exception e) {
					Singlegon.CAFEBINARIO_MEMORY.memory.entrySet().iterator().remove();
				}

				Time.sleep(1 * 60 * 1000);
			}
		}
	}

	public static CafebinarioMemory getInstance() {
		return Singlegon.CAFEBINARIO_MEMORY;
	}

	private CafebinarioMemory() {
		new Thread(new VerifierExpireTokens()).start();
	}

	private final Map<String, SecureMemoryData> memory = Collections.synchronizedMap(new TreeMap<>());

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

		/**
		 * TODO SecureMemoryData ja esta em memoria e ja possui objeto com
		 * timestamp para verificar se esta em tempo valido o codigo abaixo tem
		 * apenas o objetivo de testar a chave. A questao aqui eh que a chave
		 * sera sempre valida, senao nao estaria em memoria... avaliar se existe
		 * algum motivo para esse teste. (que eh bem pouco performatico!)
		 */
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
