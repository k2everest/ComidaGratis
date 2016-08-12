package br.com.cafebinario.register;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.ITopic;

import br.com.cafebinario.entiry.DomainAccount;
import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.register.rules.domain.CountDomainsRules;
import br.com.cafebinario.register.rules.domain.CreateDomainRules;
import br.com.cafebinario.register.rules.domain.DomainToDomainVORules;
import br.com.cafebinario.register.rules.domain.FindLastTenDomainRules;
import br.com.cafebinario.register.vo.PageVO;
import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.domain.DomainListResultVO;
import br.com.cafebinario.register.vo.result.user.ResultVO;
import br.com.cafebinario.util.HTMLUtil;

@Service
public class DomainAccountRegisterFacade {

	@Autowired
	private FindLastTenDomainRules findLastTenDomainRules;

	@Autowired
	private DomainToDomainVORules domainToDomainVORules;

	@Autowired
	private CreateDomainRules createDomainRules;

	@Autowired
	private CountDomainsRules countDomainsRules;

	@Autowired
	private ITopic<DomainAccount> domainAccountTopic;

	@Autowired
	private ITopic<EventNotifyData> eventNotifyTopic;
	
	public DomainListResultVO lisDomains(PageVO pageVO) {

		Long total = null;
		if (pageVO.getPageSize() == 1) {
			total = countDomainsRules.get();
		}

		final List<DomainAccount> userList = findLastTenDomainRules.apply(pageVO);
		final List<NewDomainVO> domainVOList = new ArrayList<>(userList.size());

		userList.forEach(user -> {
			final NewDomainVO userVO = domainToDomainVORules.apply(user);
			domainVOList.add(userVO);
		});

		return new DomainListResultVO(ResultVOBuilder.SUCCESS(), domainVOList, total);
	}

	@Transactional
	public ResultVO newDomain(final NewDomainVO domainVO) {
		try {
			final DomainAccount domain = createDomainRules.apply(domainVO);
			notify(domain);
			return ResultVOBuilder.SUCCESS();
		} catch (VerifyExistUserException e) {
			return ResultVOBuilder.ERROR_USER_HAS_EXIST();
		} catch (NotifyException e) {
			return ResultVOBuilder.ERROR_SEND_SECURE_KEY();
		}
	}

	private void notify(final DomainAccount domain) {
		domainAccountTopic.publish(domain);
		final EventNotifyData eventNotifyData = EventNotifyData.newRegisterDefaultInstance(domain.getEmailOwner(),
				HTMLUtil.packingBody(String.format("Domain %s create!", domain.getDomain())));
		eventNotifyData.setCreationDate(new Date(System.currentTimeMillis()));
		eventNotifyData.setSubject("Congratulations!!!");
		eventNotifyTopic.publish(eventNotifyData);
	}
}
