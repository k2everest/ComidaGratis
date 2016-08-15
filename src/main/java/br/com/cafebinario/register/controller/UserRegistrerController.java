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
import br.com.cafebinario.register.facade.UserAccountRegisterFacade;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.user.AuthenticationResultVO;
import br.com.cafebinario.register.vo.result.user.UserListResultVO;
import br.com.cafebinario.register.vo.user.NewUserVO;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@RestController
@RequestMapping("/user")
public class UserRegistrerController {

	@Autowired
	private UserAccountRegisterFacade userAccountRegisterFacade;

	@RequestMapping(path = "/lastTen", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public UserListResultVO lastedTenRegisters() {
		return userAccountRegisterFacade.lastTen();

	}

	@RequestMapping(path = "/new", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResultVO userRegister(@Valid @RequestBody final NewUserVO userVO) {
		final ResultVO resultVO = userAccountRegisterFacade.newUser(userVO);
		return resultVO;
	}

	@RequestMapping(path = "/confirm", method = RequestMethod.PUT)
	public void confirmRegister(@RequestParam(name = "nick", required = true) final String nick,
			@RequestParam(name = "secureKey", required = true) final String secureKey) throws ConfirmUserException {
		userAccountRegisterFacade.confirmUser(nick, secureKey);
	}

	@RequestMapping(path = "/authentication", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<AuthenticationResultVO> userAuthentication(
			@Valid @RequestBody final UserAuthenticationVO userVO) {
		final AuthenticationResultVO authenticationResultVO = userAccountRegisterFacade.auth(userVO);
		return ResponseEntity.ok(authenticationResultVO);
	}

	@RequestMapping(path = "/user/{token}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody UserAuthenticationVO getUserByToken(@PathVariable(value = "token") final String token) {
		final UserAuthenticationVO userAuthenticationVO = userAccountRegisterFacade
				.getUserAuthenticationVOByToken(token);
		return userAuthenticationVO;
	}
}