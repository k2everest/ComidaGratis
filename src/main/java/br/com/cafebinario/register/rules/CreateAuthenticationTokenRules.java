package br.com.cafebinario.register.rules;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.function.Function;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.data.util.Pair;
import org.springframework.util.Base64Utils;

import br.com.cafebinario.register.datamemory.SecureMemoryData;
import br.com.cafebinario.register.vo.UserAuthenticationVO;

public class CreateAuthenticationTokenRules implements Function<UserAuthenticationVO, SecureMemoryData> {

	public SecureMemoryData apply(UserAuthenticationVO userAuthenticationVO) {
		try {
			long currentTimeStamp = System.currentTimeMillis();
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			SecureRandom random = new SecureRandom(BigInteger.valueOf(currentTimeStamp).toByteArray());
			keyGen.init(random);
			SecretKey secretKey = keyGen.generateKey();

			/**
			 * Isso evita ataque de bruteforce...
			 * Uma vez que uma secretKey "valida" pode ser gerada facilmente...
			 * O código abaixo garante que que exista uma chave proprietaria
			 * que não pode ser reproduzida com facilidade...
			 */
			
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);

			byte[] content = (userAuthenticationVO.getDomain() + "|" + userAuthenticationVO.getEmail() + "|"
					+ userAuthenticationVO.getNick() + "|" + userAuthenticationVO.getPassword() + "|"
					+ currentTimeStamp).getBytes();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
			cipherOutputStream.write(content);
			cipherOutputStream.flush();
			cipherOutputStream.close();
			byte[] encryptedBytes = outputStream.toByteArray();

			return new SecureMemoryData(Pair.of(Base64Utils.encodeToString(encryptedBytes), secretKey),
					userAuthenticationVO, currentTimeStamp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
