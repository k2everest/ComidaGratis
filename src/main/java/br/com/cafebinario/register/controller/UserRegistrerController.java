package br.com.cafebinario.register.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.exception.ConfirmUserException;
import br.com.cafebinario.register.RegisterFacade;
import br.com.cafebinario.register.vo.UserAuthenticationVO;
import br.com.cafebinario.register.vo.NewUserVO;
import br.com.cafebinario.register.vo.result.AuthenticationResultVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.UserListResultVO;

@RestController
@RequestMapping("/register")
public class UserRegistrerController {

	@Autowired
	private RegisterFacade registerFacade;

	@RequestMapping(path = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public UserListResultVO lastedTenRegisters() {
		return registerFacade.lastTen();

	}

	@RequestMapping(path = "/new", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResultVO userRegister(@Valid @RequestBody final NewUserVO userVO) {
		ResultVO resultVO = registerFacade.newUser(userVO);
		return resultVO;
	}

	@RequestMapping(path = "/confirm", method = RequestMethod.PUT)
	public void confirmRegister(@RequestParam(name = "secureKey", required = true) final String secureKey)
			throws ConfirmUserException {
		registerFacade.confirmUser(secureKey);
	}

	@RequestMapping(path = "/authentication", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<AuthenticationResultVO> userAuthentication(
			@Valid @RequestBody final UserAuthenticationVO userVO) {
		AuthenticationResultVO authenticationResultVO = registerFacade.auth(userVO);
		return ResponseEntity.ok(authenticationResultVO);
	}
	
	@RequestMapping(path = "/user/{token}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody UserAuthenticationVO getUserByToken(
			@PathVariable(value="token") final String token) {
		UserAuthenticationVO userAuthenticationVO = registerFacade.getUserAuthenticationVOByToken(token);
		return userAuthenticationVO;
	}
}