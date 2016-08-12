package br.com.cafebinario.register.rules.user;

import java.util.function.BiFunction;

import org.jasypt.digest.StandardStringDigester;
import org.springframework.stereotype.Component;

@Component
public class CreateSecurePasswordRules implements BiFunction<String, String, String>{

	@Override
	public String apply(final String domain, final String password) {
		final StandardStringDigester standardStringDigester = new StandardStringDigester();
		standardStringDigester.setAlgorithm("SHA-512");
		standardStringDigester.setSaltSizeBytes(0);
		final String securePassword = standardStringDigester.digest(domain + password);
		return securePassword;
	}
}