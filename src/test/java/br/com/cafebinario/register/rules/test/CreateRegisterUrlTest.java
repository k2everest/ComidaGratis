package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.CreateRegisterUrlRules;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class CreateRegisterUrlTest {

	@Autowired
	private CreateRegisterUrlRules createRegisterUrlRules;
	
	@Test
	public void test(){
		String secureKey = "123456";
		String url = createRegisterUrlRules.apply(secureKey);
		Assert.assertNotNull(url);
		String[] parts = url.split("/");
		Assert.assertEquals(secureKey, parts[parts.length-1]);
	}
}