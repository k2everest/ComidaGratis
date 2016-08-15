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
import br.com.cafebinario.register.rules.user.FindUserBySecureKeyRules;
import br.com.cafebinario.repository.UserAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class FindUserBySecureKeyTest {
	
	@Autowired
	private FindUserBySecureKeyRules findUserBySecureKeyRules;
	
	@MockBean
	private UserAccountRepository userRepository;

	@Test
	public void test(){
		final String secureKey = "123456";
		BDDMockito.given(userRepository.findByNickOrSecureKey("nickBla", secureKey)).willReturn(null);
		UserAccount userAccount = findUserBySecureKeyRules.apply("nickBla", secureKey);
		Assert.assertNull(userAccount);
	}
	
	@Test
	public void testThrow(){
		final String secureKey = "123456";
		UserAccount expectedUser = null;
		BDDMockito.given(userRepository.findByNickOrSecureKey("nickBla", secureKey)).willReturn(expectedUser);
		UserAccount userAccount = findUserBySecureKeyRules.apply("nickBla", secureKey);
		Assert.assertEquals(expectedUser, userAccount);
	}
}
