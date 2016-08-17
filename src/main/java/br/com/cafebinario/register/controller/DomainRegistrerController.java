package br.com.cafebinario.register.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.facade.DomainAccountRegisterFacade;
import br.com.cafebinario.register.vo.PageVO;
import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.domain.DomainListResultVO;
import br.com.cafebinario.register.vo.result.hateos.NavigationVO;

@RestController
@RequestMapping("/domain")
public class DomainRegistrerController {

	@Autowired
	private DomainAccountRegisterFacade domainAccountRegisterFacade;

	@RequestMapping(path = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public DomainListResultVO lisDomains(
			@RequestParam(value = "pageNumber", required = true, defaultValue = "1") final Integer pageNumber,
			@RequestParam(value = "pageSize", required = true, defaultValue = "10") Integer pageSize) {
		final PageVO pageVO = new PageVO(pageNumber, pageSize);
		final List<NewDomainVO> domainVOList = domainAccountRegisterFacade.lisDomains(pageVO);
		final Long registerCount = domainAccountRegisterFacade.countDomainsRules();

		final long totalPages = (registerCount / pageSize) + ((registerCount % pageSize) > 0 ? 1 : 0);
		String nextPage = pageNumber == totalPages ? null
				: "/domain?pageNumber=" + (pageNumber + 1) + "&pageSize=" + pageSize;
		final List<NavigationVO> navigations = nextPage == null ? null
				: Arrays.asList(new NavigationVO("GET", "next page", nextPage),
						new NavigationVO("POST", "new domain", "/domain/new"));

		return new DomainListResultVO(ResultVOBuilder.SUCCESS(navigations), domainVOList,
				pageVO.getPageNumber() == 1 ? registerCount : null);
	}

	@RequestMapping(path = "/new", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResultVO userRegister(@Valid @RequestBody final NewDomainVO domainVO) {
		try {
			domainAccountRegisterFacade.newDomain(domainVO);

			List<NavigationVO> navigations = Arrays.asList(
					new NavigationVO("GET", "list domains", "/domain?pageNumber=1&pageSize=10"),
					new NavigationVO("POST", "new domain", "/domain/new"));
			return ResultVOBuilder.SUCCESS(navigations);
		} catch (VerifyExistUserException e) {
			return ResultVOBuilder.ERROR_USER_HAS_EXIST();
		} catch (NotifyException e) {
			return ResultVOBuilder.ERROR_SEND_SECURE_KEY();
		}
	}
}
