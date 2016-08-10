package br.com.cafebinario.register.rules.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.register.rules.DecriptyDataRules;
import br.com.cafebinario.register.rules.EncriptyDataRules;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class EncriptyDecryptDataTest {

	@Autowired
	private EncriptyDataRules encriptyDataRules;
	
	@Autowired
	private DecriptyDataRules decriptyDataRules;
	
	@Test
	public void test(){
		String expectedContent = "TESTE";
		String encryptedData = encriptyDataRules.apply(new String("123456"), expectedContent);
		String content = decriptyDataRules.apply(new String("123456"), encryptedData);
		Assert.assertEquals(expectedContent, content);
	}
}