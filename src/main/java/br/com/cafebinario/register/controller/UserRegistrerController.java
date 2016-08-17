package br.com.cafebinario.register.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cafebinario.exception.ConfirmUserException;
import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.facade.UserAccountRegisterFacade;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.hateos.NavigationVO;
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
		final List<NewUserVO> userVOList = userAccountRegisterFacade.lastTen();

		List<NavigationVO> navigations = Arrays.asList(
				new NavigationVO("GET", "last ten users register", "/user/lastTen"),
				new NavigationVO("POST", "new user", "/user/new"),
				new NavigationVO("POST", "user authentication", "/user/authentication"));

		return new UserListResultVO(ResultVOBuilder.SUCCESS(navigations), userVOList);

	}

	@RequestMapping(path = "/new", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResultVO userRegister(@Valid @RequestBody(required = true) final NewUserVO userVO) {

		try {
			userAccountRegisterFacade.newUser(userVO);
			
			List<NavigationVO> navigations = Arrays.asList(
					new NavigationVO("PUT", "confirm register", "/user/confirm&nick={nick}&secureKey={secureKey}"),
					new NavigationVO("POST", "user authentication", "/user/authentication"));
			return ResultVOBuilder.SUCCESS(navigations);
		} catch (VerifyExistUserException e) {
			return ResultVOBuilder.ERROR_USER_HAS_EXIST();
		} catch (NotifyException e) {
			return ResultVOBuilder.ERROR_SEND_SECURE_KEY();
		}
	}

	@RequestMapping(path = "/confirm", method = RequestMethod.PUT)
	public void confirmRegister(@RequestParam(name = "nick", required = true) final String nick,
			@RequestParam(name = "secureKey", required = true) final String secureKey) throws ConfirmUserException {
		userAccountRegisterFacade.confirmUser(nick, secureKey);
	}

	@RequestMapping(path = "/authentication", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody AuthenticationResultVO userAuthentication(
			@Valid @RequestBody final UserAuthenticationVO userVO) {
		final String secureKey = userAccountRegisterFacade.auth(userVO);
		List<NavigationVO> navigations= Arrays.asList(
				new NavigationVO("GET", "find user authenticated", "/user/{token}"),
				new NavigationVO("POST", "donate", "/{domain}/{user}/donate"),
				new NavigationVO("GET", "benefts", "/{domain}/{user}/benefts"));
		
		return new AuthenticationResultVO(secureKey, ResultVOBuilder.SUCCESS(navigations));
	}

	@RequestMapping(path = "/user/{nick}/{token}", method = RequestMethod.GET, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody UserAuthenticationVO getUserByToken(@PathVariable(value = "nick") final String nick, @PathVariable(value = "token") final String token) {
		final UserAuthenticationVO userAuthenticationVO = userAccountRegisterFacade
				.getUserAuthenticationVOByToken(nick, token);
		return userAuthenticationVO;
	}
}