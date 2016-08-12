package br.com.cafebinario.register.rules.user;

import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;
import br.com.cafebinario.repository.UserAccountRepository;

@Component
public class UserAuthenticationRules implements BiConsumer<UserAuthenticationVO, String> {

	@Autowired
	private ValidateUserAuthenticationRules validateUserAuthenticationRules;
	
	@Autowired
	private VerifySecurePasswordRules verifySecurePasswordRules;

	@Autowired
	private UserAccountRepository userAccountRepository;

	public void accept(final UserAuthenticationVO userAuthenticationVO, final String decriptyUserPassword) throws VerifyExistUserException {
		validateUserAuthenticationRules.accept(userAuthenticationVO);
		final UserAccount userAccount = userAccountRepository.existUserByEmailOrNick(userAuthenticationVO.getEmail(),
				userAuthenticationVO.getNick());
		
		if (userAccount == null)
			throw new VerifyExistUserException();
		
		verifySecurePasswordRules.accept(userAccount, new UserAuthenticationVO(userAuthenticationVO, decriptyUserPassword));
	}
}
