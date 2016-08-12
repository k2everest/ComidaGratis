package br.com.cafebinario.register.rules.user;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class FindLastTenUsersRules implements Supplier<List<UserAccount>>{
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	public List<UserAccount> get(){
		
		/**
		 * TODO esse metodo devera fazer busca em memoria
		 */

		final PageRequest pageRequest = new PageRequest(1, 10, sortByUserAlterDate());
		return userAccountRepository.findAll(pageRequest).getContent();
	}

	private Sort sortByUserAlterDate() {
		return new Sort(UserAccount.sortByUserAlterDate());
	}
}