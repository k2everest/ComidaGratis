package br.com.cafebinario.register.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.register.facade.DomainAccountRegisterFacade;
import br.com.cafebinario.register.vo.PageVO;
import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.domain.DomainListResultVO;

@RestController
@RequestMapping("/domain")
public class DomainRegistrerController {

	@Autowired
	private DomainAccountRegisterFacade domainAccountRegisterFacade;

	@RequestMapping(path = "/", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public DomainListResultVO lisDomains(@RequestParam(value = "pageNumber") final Integer pageNumber,
			@RequestParam(value = "pageSize") Integer pageSize) {
		final PageVO pageVO = new PageVO(pageNumber, pageSize);
		final DomainListResultVO domainListResultVO = domainAccountRegisterFacade.lisDomains(pageVO);
		return domainListResultVO;
	}

	@RequestMapping(path = "/new", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResultVO userRegister(@Valid @RequestBody final NewDomainVO domainVO) {
		final ResultVO resultVO = domainAccountRegisterFacade.newDomain(domainVO);
		return resultVO;
	}
}
