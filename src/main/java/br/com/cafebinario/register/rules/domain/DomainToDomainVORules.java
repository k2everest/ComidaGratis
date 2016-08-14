package br.com.cafebinario.register.rules.domain;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import br.com.cafebinario.entity.DomainAccount;
import br.com.cafebinario.register.vo.domain.NewDomainVO;

@Component
public class DomainToDomainVORules implements Function<DomainAccount, NewDomainVO> {

	@Override
	public NewDomainVO apply(final DomainAccount domain) {
		final NewDomainVO domainVO = new NewDomainVO();
		domainVO.setDescription(domain.getDescription());
		domainVO.setDomain(domain.getDomain());
		domainVO.setEmailOwner(domain.getEmailOwner());
		return domainVO;
	}
}