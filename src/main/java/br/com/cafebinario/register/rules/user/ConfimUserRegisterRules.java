package br.com.cafebinario.register.rules.user;

import java.util.Date;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.exception.ConfirmUserException;

@Component
public class ConfimUserRegisterRules implements BiFunction<String, String, UserAccount> {

	@Autowired
	private FindUserBySecureKeyRules findUserBySecureKey;
	
	@Override
	public UserAccount apply(final String nick, final String secureKey) {
		Assert.hasLength(nick);
		Assert.hasLength(secureKey);
		
		final UserAccount user = findUserBySecureKey.apply(nick, secureKey);
		verifyAndEnrichUser(user);
		return user;
	}
	
	private void verifyAndEnrichUser(final UserAccount user) {
		if(user == null) throw new ConfirmUserException();
		user.setAuthorized(Boolean.TRUE);
		user.setAlterDate(new Date(System.currentTimeMillis()));
	}
}