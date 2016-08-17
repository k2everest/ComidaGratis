package br.com.cafebinario.register.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.api.FlowLinkVO;
import br.com.cafebinario.register.vo.result.api.LinkVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.domain.DomainListResultVO;
import br.com.cafebinario.register.vo.result.user.AuthenticationResultVO;
import br.com.cafebinario.register.vo.result.user.UserListResultVO;
import br.com.cafebinario.register.vo.user.NewUserVO;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@RestController
@RequestMapping("/api")
public class RegisterApiController {

	@RequestMapping(path = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody FlowLinkVO showRegisterApi() {

		final ResultVO resultResponse = ResultVOBuilder.BLANCK();
		final NewDomainVO newDomainRequest = NewDomainVO.createUserBlanck();
		final NewUserVO newUserRequest = NewUserVO.createUserBlanck();
		final UserAuthenticationVO userAuthenticationRequest = UserAuthenticationVO.createUserBlanck();
		final AuthenticationResultVO authenticationResultResponse = AuthenticationResultVO.createUserBlanck();
		final UserListResultVO userListResultResponse = UserListResultVO.createUserBlanck();
		final DomainListResultVO domainListResultResponse = DomainListResultVO.createUserBlanck();
		
		final List<LinkVO> links = new ArrayList<>();
		final List<LinkVO> nextUserNewLinks = new ArrayList<>();
		final List<LinkVO> optionUserLinks = new ArrayList<>();
		final List<LinkVO> chooses = new ArrayList<>();
		

		links.add(new LinkVO("list domains", RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/domain?pageNumber={pageNumber}&pageSize={pageSize}", null, null, domainListResultResponse));

		links.add(new LinkVO("new domain", RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/domain/new", null, newDomainRequest, resultResponse));

		links.add(new LinkVO("last ten users register", RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/user/lastTen", null, null, userListResultResponse));

		nextUserNewLinks.add(new LinkVO("confirm register", RequestMethod.PUT.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/user/confirm?nick={nick}&secureKey={secureKey}", null, null, null));

		links.add(new LinkVO("new user", RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/user/new", nextUserNewLinks, newUserRequest, resultResponse));

		chooses.add(new LinkVO("donate", RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/{domain}/{nick}/donate", null, null, null));
		chooses.add(new LinkVO("benefts", RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/{domain}/{nick}/benefits", null, null, null));


		links.add(new LinkVO("user authentication", RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/user/authentication", optionUserLinks, userAuthenticationRequest, authenticationResultResponse));
		
		links.add(new LinkVO("find user authenticated", RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE,
				"http://cafebinario.com.br/user/{token}", null, null, userAuthenticationRequest));

		return new FlowLinkVO(links);
	}
}
