package br.com.cafebinario.register.rules.user;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

import br.com.cafebinario.funcional.ABRFunction;

@Component
public class EncriptyDataRules implements ABRFunction<String, String, String>{

	public String apply(final String password, final String content){
		final StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setPassword(password);
	    return standardPBEStringEncryptor.encrypt(content);
	}
}