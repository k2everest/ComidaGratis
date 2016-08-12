package br.com.cafebinario.register.rules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hazelcast.core.ITopic;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.funcional.ABConsumer;
import br.com.cafebinario.notify.data.EventNotifyData;

@Component
public class SendSecureKeyRules implements ABConsumer<String, String>{

	@Autowired
	private ITopic<EventNotifyData> eventNotifyTopic;

	public void accept(final String to, final String url) throws NotifyException {
		eventNotifyTopic.publish(EventNotifyData.newRegisterDefaultInstance(to, url));
	}
}
