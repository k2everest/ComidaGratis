package br.com.cafebinario.register.rules.user;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.function.Function;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import br.com.cafebinario.register.datamemory.SecureMemoryData;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@Component
public class CreateAuthenticationTokenRules implements Function<UserAuthenticationVO, SecureMemoryData> {

	public SecureMemoryData apply(final UserAuthenticationVO userAuthenticationVO) {
		try {
			long currentTimeStamp = System.currentTimeMillis();
			final KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			final SecureRandom random = new SecureRandom(BigInteger.valueOf(currentTimeStamp).toByteArray());
			keyGen.init(random);
			final SecretKey secretKey = keyGen.generateKey();

			/**
			 * Isso evita ataque de bruteforce...
			 * Uma vez que uma secretKey "valida" pode ser gerada facilmente...
			 * O codigo abaixo garante que que exista uma chave proprietaria
			 * que nao pode ser reproduzida com facilidade...
			 */
			
			final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			final byte[] content = (userAuthenticationVO.getDomain() + "|" + userAuthenticationVO.getEmail() + "|"
					+ userAuthenticationVO.getNick() + "|" + userAuthenticationVO.getPassword() + "|"
					+ currentTimeStamp).getBytes();

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
			cipherOutputStream.write(content);
			cipherOutputStream.flush();
			cipherOutputStream.close();
			final byte[] encryptedBytes = outputStream.toByteArray();

			return new SecureMemoryData(Pair.of(Base64Utils.encodeToString(encryptedBytes), secretKey),
					userAuthenticationVO, currentTimeStamp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
