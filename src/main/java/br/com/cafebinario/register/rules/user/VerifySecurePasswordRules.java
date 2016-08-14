package br.com.cafebinario.register.rules.user;

import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@Component
public class VerifySecurePasswordRules implements BiConsumer<UserAccount, UserAuthenticationVO> {

	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;

	public void accept(final UserAccount userAccount, final UserAuthenticationVO userAuthenticationVO) {
		final String securePassword = createSecurePasswordRules.apply(userAuthenticationVO.getDomain(),
				userAuthenticationVO.getPassword());

		Assert.isTrue(securePassword.equals(userAccount.getPassword()));
	}
}
