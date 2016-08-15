package br.com.cafebinario.register.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.register.vo.result.api.FlowLinkVO;
import br.com.cafebinario.register.vo.result.api.LinkVO;

@RestController
@RequestMapping("/api")
public class RegisterApiController {

	@RequestMapping(path = "", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody FlowLinkVO showRegisterApi() {
		final List<LinkVO> links = new ArrayList<>();
		final List<LinkVO> nextUserNewLinks = new ArrayList<>();
		final List<LinkVO> optionUserLinks = new ArrayList<>();
		List<LinkVO> chooses = new ArrayList<>();
		
		links.add(new LinkVO(RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/domain?pageNumber={pageNumber}&pageSize={pageSize}", null));
		links.add(new LinkVO(RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/domain/new", null));
		
		links.add(new LinkVO(RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/user/lastTen", null));
		
		nextUserNewLinks.add(new LinkVO(RequestMethod.PUT.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/user/confirm?nick={nick}&secureKey={secureKey}", null));
		links.add(new LinkVO(RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/user/new", nextUserNewLinks));
		
		chooses.add(new LinkVO(RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/{domain}/{nick}/donate", null));
		chooses.add(new LinkVO(RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/{domain}/{nick}/benefits", null));
		
		
		optionUserLinks.add(new LinkVO(RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/{domain}/{nick}", chooses));
		
		links.add(new LinkVO(RequestMethod.POST.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/user/authentication", optionUserLinks));
		links.add(new LinkVO(RequestMethod.GET.name(), MediaType.APPLICATION_JSON_VALUE, "http://cafebinario.com.br/user/{token}", null));
		
		
		return new FlowLinkVO(links);
	}
}
