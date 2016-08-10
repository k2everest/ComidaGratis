package br.com.cafebinario.register.rules;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.exception.VerifyExistUserException;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.vo.NewUserVO;

@Component
public class CreateUserRules implements Function<NewUserVO, UserAccount>{

	@Autowired
	private VerifyUserVORules verifyUserVORules;
	
	@Autowired
	private CreateSecureKeyRules createSecureKeyRules;
	
	@Autowired
	private VerifyExistUserRules verifyExistUser;

	public UserAccount apply(final NewUserVO userVO) throws VerifyExistUserException {

		verifyUserVORules.accept(userVO);
		final String secureKey = createSecureKeyRules.apply(userVO);
		verifySecureKey(secureKey);
		
		final UserAccount user = new UserAccount(userVO, secureKey);
		verifyExistUser.accept(user.getPassword(), user.getSecureKey());
		
		return user;
	}
	
	private void verifySecureKey(final String secureKey) {
		Assert.hasLength(secureKey);
	}
}