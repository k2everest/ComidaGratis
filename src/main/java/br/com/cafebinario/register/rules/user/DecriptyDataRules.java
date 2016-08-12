package br.com.cafebinario.register.rules.user;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

@Component
public class DecriptyDataRules {

	public String apply(final String password, final String encryptedData){
		final StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setPassword(password);
	    return standardPBEStringEncryptor.decrypt(encryptedData);
	}
}
