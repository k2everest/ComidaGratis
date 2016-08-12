package br.com.cafebinario.register.rules.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.cafebinario.main.Main;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.interfaces.Sender;
import br.com.cafebinario.register.rules.user.SendSecureKeyRules;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class })
public class SendSecureKeyTest {

	@Autowired
	private SendSecureKeyRules sendSecureKeyRules;

	@Test
	public void testHandle() {
		EventNotifyData eventNotifyData = new EventNotifyData();
		eventNotifyData.setTo(new String[] { "ale" });
		eventNotifyData.setContent("teste");
		BDDMockito.mock(Sender.class).execute(eventNotifyData);
		sendSecureKeyRules.accept("ale", "teste");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThrow() {
		EventNotifyData eventNotifyData = EventNotifyData.newRegisterDefaultInstance(null, null);
		BDDMockito.mock(Sender.class).execute(eventNotifyData);
		sendSecureKeyRules.accept(null, null);
	}
}
