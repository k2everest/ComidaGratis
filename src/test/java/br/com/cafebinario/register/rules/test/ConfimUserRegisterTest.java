package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.ConfimUserRegisterRules;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.rules.user.FindUserBySecureKeyRules;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class ConfimUserRegisterTest {

	@Autowired
	private ConfimUserRegisterRules confimUserRegisterRules;

	@MockBean
	private FindUserBySecureKeyRules findUserBySecureKey;
	
	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;

	@Test
	public void test() {
		NewUserVO newUserVO = NewUserVO.createUserVOJUnitValidTest();
		String secureKey = "123456";
		String securePassword = createSecurePasswordRules.apply(newUserVO.getDomain(), newUserVO.getPassword());
		UserAccount expectedUserAccount = new UserAccount(newUserVO, secureKey, securePassword);
		BDDMockito.given(findUserBySecureKey.apply(secureKey)).willReturn(expectedUserAccount);
		UserAccount userAccount = confimUserRegisterRules.apply(secureKey);
		Assert.assertEquals(expectedUserAccount, userAccount);
	}
}