package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.user.CreateSecureKeyRules;
import br.com.cafebinario.register.rules.user.DecriptyDataRules;
import br.com.cafebinario.register.vo.user.NewUserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class CreateSecureKeyTest {
	
	@Autowired
	private CreateSecureKeyRules createSecureKeyRules;
	
	@Autowired
	private DecriptyDataRules decriptyDataRules;
	
	@Test
	public void test(){
		NewUserVO userVO = NewUserVO.createUserVOJUnitValidTest();
		String encryptedDataBase64 = createSecureKeyRules.apply(userVO);
		String encryptedData = new String(Base64Utils.decodeFromString(encryptedDataBase64));
		String decriptyData = decriptyDataRules.apply(userVO.getPassword(), encryptedData);
		Assert.assertEquals(userVO.getInternalString(), decriptyData);
	}
}
