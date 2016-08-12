package br.com.cafebinario.register.rules.user;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.vo.user.NewUserVO;

@Component
public class CreateUserRules implements Function<NewUserVO, UserAccount>{

	@Autowired
	private VerifyUserVORules verifyUserVORules;
	
	@Autowired
	private CreateSecureKeyRules createSecureKeyRules;
	
	@Autowired
	private VerifyExistUserRules verifyExistUser;
	
	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;

	public UserAccount apply(final NewUserVO newUserVO) throws VerifyExistUserException {

		verifyUserVORules.accept(newUserVO);
		final String secureKey = createSecureKeyRules.apply(newUserVO);
		verifySecureKey(secureKey);
		final String securePassword = createSecurePasswordRules.apply(newUserVO.getDomain(), newUserVO.getPassword());
		final UserAccount user = new UserAccount(newUserVO, secureKey, securePassword);
		verifyExistUser.accept(user.getPassword(), user.getSecureKey());
		
		return user;
	}
	
	private void verifySecureKey(final String secureKey) {
		Assert.hasLength(secureKey);
	}
}
