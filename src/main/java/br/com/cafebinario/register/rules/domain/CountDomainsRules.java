package br.com.cafebinario.register.rules.domain;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.repository.AccountDomainRepository;

@Component
public class CountDomainsRules implements Supplier<Long>{
	
	@Autowired
	private AccountDomainRepository domainAccountRepository;
	
	public Long get(){
		return domainAccountRepository.count();
	}
}
