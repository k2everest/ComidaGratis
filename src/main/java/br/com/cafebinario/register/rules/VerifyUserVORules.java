package br.com.cafebinario.register.rules;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.register.vo.NewUserVO;

@Component
public class VerifyUserVORules implements Consumer<NewUserVO>{

	public void accept(final NewUserVO userVO) {
		Assert.notNull(userVO);
		Assert.hasLength(userVO.getEmail());
		Assert.hasLength(userVO.getNick());
		Assert.hasLength(userVO.getPassword());
	}
}
