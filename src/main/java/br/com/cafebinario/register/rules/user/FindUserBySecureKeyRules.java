package br.com.cafebinario.register.rules.user;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class FindUserBySecureKeyRules implements Function<String, UserAccount>{
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	public UserAccount apply(final String secureKey) {
		return userAccountRepository.findUserBySecureKey(secureKey);
	}
}
