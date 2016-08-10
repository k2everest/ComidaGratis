package br.com.cafebinario.register.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.funcional.ABConsumer;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class VerifyExistUserRules implements ABConsumer<String, String>{

	@Autowired
	private UserAccountRepository userAccountRepository;

	public void accept(final String nick, final String secureKey) throws VerifyExistUserException {
		Assert.hasText(nick);
		Assert.hasText(secureKey);
		final UserAccount user = userAccountRepository.findByNickOrSecureKey(nick, secureKey);
		
		if(user == null) throw new VerifyExistUserException();
	}
}