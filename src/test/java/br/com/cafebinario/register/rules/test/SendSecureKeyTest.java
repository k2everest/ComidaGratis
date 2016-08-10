package br.com.cafebinario.register.rules.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.main.Main;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.interfaces.Sender;
import br.com.cafebinario.register.rules.SendSecureKeyRules;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class SendSecureKeyTest {

	@Autowired
	private SendSecureKeyRules sendSecureKeyRules;
	
	@MockBean
	private Sender serder;

	@Test
	public void testHandle() {
		EventNotifyData eventNotifyData = new EventNotifyData();
		BDDMockito.doNothing().when(serder).execute(eventNotifyData);
		sendSecureKeyRules.accept(null, null);
	}
	
	@Test(expected=NotifyException.class)
	public void testThrow() {
		EventNotifyData eventNotifyData = EventNotifyData.newRegisterDefaultInstance(null, null);
		BDDMockito.doThrow(NotifyException.class).when(serder).execute(eventNotifyData);
		sendSecureKeyRules.accept(null, null);
	}
}
