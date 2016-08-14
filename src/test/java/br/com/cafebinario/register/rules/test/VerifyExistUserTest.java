package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.VerifyExistUserRules;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class VerifyExistUserTest {

	@Autowired
	private VerifyExistUserRules verifyExistUserRules;

	@Test(expected=IllegalArgumentException.class)
	public void testSpaces() {
		verifyExistUserRules.apply("             ", "                ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNull() {
		verifyExistUserRules.apply(null, null);
	}
	
	@Test
	public void testNickAndSecureKey() {
		Assert.assertFalse(verifyExistUserRules.apply("-----", "-----"));
	}
}