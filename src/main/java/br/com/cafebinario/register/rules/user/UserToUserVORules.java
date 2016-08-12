package br.com.cafebinario.register.rules.user;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.register.vo.user.NewUserVO;

@Component
public class UserToUserVORules implements Function<UserAccount, NewUserVO> {

	@Override
	public NewUserVO apply(final UserAccount user) {
		final NewUserVO userVO = new NewUserVO();
		userVO.setDomain(user.getDomainAccount() != null ? user.getDomainAccount().getDomain() : null);
		userVO.setNick(user.getNick());
		userVO.setEmail(user.getEmail());
		userVO.setPassword(user.getPassword());
		return userVO;
	}
}