package br.com.cafebinario.register.rules.domain;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import br.com.cafebinario.entity.DomainAccount;
import br.com.cafebinario.register.vo.domain.NewDomainVO;

@Component
public class CreateDomainRules implements Function<NewDomainVO, DomainAccount>{

	public DomainAccount apply(final NewDomainVO domainVO) {
		final DomainAccount domainAccount = new DomainAccount(domainVO);
		return domainAccount;
	}
}