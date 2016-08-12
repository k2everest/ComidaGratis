package br.com.cafebinario.register.rules.domain;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.DomainAccount;
import br.com.cafebinario.repository.AccountDomainRepository;

@Component
public class PersistDomainRules implements Consumer<DomainAccount> {

	@Autowired
	private AccountDomainRepository domainAccountRepository;
	
	@Override
	public void accept(final DomainAccount domainAccount) {
		domainAccountRepository.save(domainAccount);
	}
}