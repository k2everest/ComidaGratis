package br.com.cafebinario.register.rules.user;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class FindUserBySecureKeyRules implements BiFunction<String, String, UserAccount>{
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	public UserAccount apply(final String nick, final String secureKey) {
		return userAccountRepository.findUserByNickAndSecureKey(nick, secureKey);
	}
}