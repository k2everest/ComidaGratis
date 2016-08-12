package br.com.cafebinario.register.rules.user;

import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class PersistUserRules implements Consumer<UserAccount>{
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Transactional
	public void accept(final UserAccount user){
		userAccountRepository.save(user);
	}

}
