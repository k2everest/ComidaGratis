package br.com.cafebinario.register.rules.user;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class VerifyExistUserRules implements BiFunction<String, String, Boolean>{

	@Autowired
	private UserAccountRepository userAccountRepository;

	public Boolean apply(final String nick, final String secureKey) throws VerifyExistUserException {
		Assert.hasText(nick);
		Assert.hasText(secureKey);
		final UserAccount user = userAccountRepository.findByNickOrSecureKey(nick, secureKey);
		
		return Boolean.valueOf(user != null);
	}
}