package br.com.cafebinario.register.rules.user;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.exception.ConfirmUserException;

@Component
public class ConfimUserRegisterRules implements Function<String, UserAccount> {

	@Autowired
	private FindUserBySecureKeyRules findUserBySecureKey;
	
	@Override
	public UserAccount apply(final String secureKey) {
		Assert.hasLength(secureKey);
		
		/**
		 * TODO findUserBySecureKey.apply deve ser buscado em memoria
		 */
		final UserAccount user = findUserBySecureKey.apply(secureKey);
		verifyAndEnrichUser(user);
		return user;
	}
	
	private void verifyAndEnrichUser(final UserAccount user) {
		if(user == null) throw new ConfirmUserException();
		user.setAuthorized(Boolean.TRUE);
		user.setAlterDate(new Date(System.currentTimeMillis()));
	}
}