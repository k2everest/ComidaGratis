package br.com.cafebinario.register.rules;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.vo.UserAuthenticationVO;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class UserAuthenticationRules implements Consumer<UserAuthenticationVO> {

	@Autowired
	private ValidateUserAuthenticationRules validateUserAuthenticationRules;

	@Autowired
	private UserAccountRepository userAccountRepository;

	public void accept(UserAuthenticationVO userAuthenticationVO) throws VerifyExistUserException {
		validateUserAuthenticationRules.accept(userAuthenticationVO);
		UserAccount userAccount = userAccountRepository.existUserByEmailOrNick(userAuthenticationVO.getEmail(),
				userAuthenticationVO.getNick());
		if (userAccount == null)
			new VerifyExistUserException();
	}
}
