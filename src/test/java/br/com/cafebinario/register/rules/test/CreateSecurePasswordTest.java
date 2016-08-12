package br.com.cafebinario.register.rules.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class CreateSecurePasswordTest {

	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;
	
	@Test
	public void testSUCCESS(){
		NewUserVO userAuthenticationVO = NewUserVO.createUserVOJUnitValidTest();
		createSecurePasswordRules.apply(userAuthenticationVO.getDomain(), userAuthenticationVO.getPassword());
	}
}