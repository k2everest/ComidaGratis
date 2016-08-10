package br.com.cafebinario.register.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.interfaces.Sender;
import br.com.cafebinario.register.funcional.ABConsumer;

@Component
public class SendSecureKeyRules implements ABConsumer<String, String>{

	@Autowired
	private Sender serder;

	public void accept(final String to, final String url) throws NotifyException {
		serder.execute(
				EventNotifyData.newRegisterDefaultInstance(to, url));
	}
}
