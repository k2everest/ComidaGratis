package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.rules.ConfimUserRegisterRules;
import br.com.cafebinario.register.rules.FindUserBySecureKeyRules;
import br.com.cafebinario.register.vo.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class ConfimUserRegisterTest {

	@Autowired
	private ConfimUserRegisterRules confimUserRegisterRules;

	@MockBean
	private FindUserBySecureKeyRules findUserBySecureKey;

	@Test
	public void test() {
		String secureKey = "123456";
		UserAccount expectedUserAccount = new UserAccount(NewUserVO.createUserVOJUnitValidTest(), secureKey);
		BDDMockito.given(findUserBySecureKey.apply(secureKey)).willReturn(expectedUserAccount);
		UserAccount userAccount = confimUserRegisterRules.apply(secureKey);
		Assert.assertEquals(expectedUserAccount, userAccount);
	}
}