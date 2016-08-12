package br.com.cafebinario.register.rules.user;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import br.com.cafebinario.register.vo.user.NewUserVO;

@Component
public class CreateSecureKeyRules implements Function<NewUserVO, String> {

	@Autowired
	private EncriptyDataRules encriptyDataRules;

	public String apply(final NewUserVO userVO) {
		return Base64Utils
				.encodeToString(encriptyDataRules.apply(userVO.getPassword(), userVO.getInternalString()).getBytes());
	}
}
