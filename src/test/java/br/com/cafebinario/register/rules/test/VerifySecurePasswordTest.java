package br.com.cafebinario.register.rules.test;

import org.jasypt.digest.StandardStringDigester;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.entity.UserAccount;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.CreateSecurePasswordRules;
import br.com.cafebinario.register.rules.user.VerifySecurePasswordRules;
import br.com.cafebinario.register.vo.user.NewUserVO;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class VerifySecurePasswordTest {

	@Autowired
	private VerifySecurePasswordRules verifySecurePasswordRules;
	
	@Autowired
	private CreateSecurePasswordRules createSecurePasswordRules;
	
	@Test
	public void test(){
		final NewUserVO newUserVO = NewUserVO.createUserVOJUnitValidTest();
		final String secureKey = "123456";
		final String securePassword = createSecurePasswordRules.apply(newUserVO.getDomain(), newUserVO.getPassword());
		
		final UserAccount userAccount = new UserAccount(newUserVO, secureKey, securePassword);
		final UserAuthenticationVO userAuthenticationVO = UserAuthenticationVO.create(newUserVO, "123456");
		verifySecurePasswordRules.accept(userAccount, userAuthenticationVO);
	}
	
	@Test
	public void testHashSHA512ConsistencyBetweenRunsWithNoSalt() throws Exception {
	    StandardStringDigester digester1 = new StandardStringDigester();
	    digester1.setSaltSizeBytes(0);
	    digester1.setAlgorithm("SHA-512");
	    final String expectedHash = digester1.digest("123456");

	    StandardStringDigester digester2 = new StandardStringDigester();
	    digester2.setSaltSizeBytes(0);
	    digester2.setAlgorithm("SHA-512");
	    String hashAgain = digester2.digest("123456");
	    
	    Assert.assertEquals(expectedHash, hashAgain);
	}
}
