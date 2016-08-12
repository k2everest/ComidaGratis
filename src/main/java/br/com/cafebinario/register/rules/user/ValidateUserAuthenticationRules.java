package br.com.cafebinario.register.rules.user;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@Component
public class ValidateUserAuthenticationRules implements Consumer<UserAuthenticationVO>{

	public void accept(final UserAuthenticationVO userAuthenticationVO) {
		Assert.notNull(userAuthenticationVO);
		Assert.hasText(userAuthenticationVO.getDomain());
		Assert.hasText(userAuthenticationVO.getPassword());
		Assert.isTrue(Boolean.logicalOr(StringUtils.hasText(userAuthenticationVO.getEmail()),
				StringUtils.hasText(userAuthenticationVO.getNick())));
	}

}
