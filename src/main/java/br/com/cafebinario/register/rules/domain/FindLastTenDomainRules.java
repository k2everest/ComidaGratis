package br.com.cafebinario.register.rules.domain;

import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.DomainAccount;
import br.com.cafebinario.register.vo.PageVO;
import br.com.cafebinario.repository.AccountDomainRepository;

@Component
public class FindLastTenDomainRules implements Function<PageVO, List<DomainAccount>> {

	@Autowired
	private AccountDomainRepository accountDomainRepository;

	public List<DomainAccount> apply(final PageVO pageVO) {
		/**
		 * TODO esse metodo devera fazer busca em memoria
		 */
		final PageRequest pageRequest = new PageRequest(pageVO.getPageNumber(), pageVO.getPageSize(),
				sortByUserAlterDate());
		return accountDomainRepository.findAll(pageRequest).getContent();
	}

	private Sort sortByUserAlterDate() {
		return new Sort(DomainAccount.sortByUserAlterDate());
	}
}