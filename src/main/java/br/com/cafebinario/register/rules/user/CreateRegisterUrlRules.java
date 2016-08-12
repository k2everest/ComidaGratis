package br.com.cafebinario.register.rules.user;

import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class CreateRegisterUrlRules implements Function<String, String>{
	
	public String apply(final String secureKey) {
		return String.format("http://cafebinario.com.br/register/%s", secureKey);
	}
}
