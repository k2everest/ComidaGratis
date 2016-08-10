package br.com.cafebinario.register.rules.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.rules.PersistUserRules;
import br.com.cafebinario.register.vo.NewUserVO;
import br.com.cafebinario.repository.UserAccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class PersistUserTest{
	
	@Autowired
	private PersistUserRules persistUserRules;
	
	@MockBean
	private UserAccountRepository userRepository;
	
	@Test
	public void testHandle(){
		final UserAccount user = new UserAccount(NewUserVO.createUserVOJUnitValidTest(), "123456");
		BDDMockito.given(userRepository.save(user)).willReturn(user);
		persistUserRules.accept(user);
	}
	
	@Test(expected=JpaSystemException.class)
	public void testConstraint(){
		final UserAccount user = new UserAccount(NewUserVO.createUserVOJUnitInvalidTest(), null);
		BDDMockito.given(userRepository.save(user)).willReturn(user);
		persistUserRules.accept(user);
	}
}