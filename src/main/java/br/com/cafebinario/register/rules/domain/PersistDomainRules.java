package br.com.cafebinario.register.rules.domain;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entity.DomainAccount;
import br.com.cafebinario.repository.DomainAccountRepository;

@Component
public class PersistDomainRules implements Consumer<DomainAccount> {

	@Autowired
	private DomainAccountRepository domainAccountRepository;
	
	@Override
	public void accept(final DomainAccount domainAccount) {
		domainAccountRepository.save(domainAccount);
	}
}