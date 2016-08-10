package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.entiry.UserAccount;
import br.com.cafebinario.register.rules.UserToUserVORules;
import br.com.cafebinario.register.vo.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class UserToUserVOTest {

	@Autowired
	private UserToUserVORules userToUserVORules;
	
	@Test
	public void test() {
		NewUserVO userVO = userToUserVORules.apply(new UserAccount(NewUserVO.createUserVOJUnitValidTest(), ""));
		Assert.assertEquals(userVO, NewUserVO.createUserVOJUnitValidTest());
	}
}