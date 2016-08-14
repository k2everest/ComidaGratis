package br.com.cafebinario.register.rules.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.hazelcast.core.ITopic;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.funcional.ABConsumer;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.register.listener.EventNotifyDataEventListener;

@Component
public class SendSecureKeyRules implements ABConsumer<String, String>{

	@Autowired
	private ITopic<EventNotifyData> eventNotifyTopic;
	
	@Autowired
	private EventNotifyDataEventListener eventNotifyDataEventListener;

	public void accept(final String to, final String url) throws NotifyException {
		Assert.hasText(to);
		Assert.hasText(url);
		eventNotifyTopic.addMessageListener(eventNotifyDataEventListener);
		eventNotifyTopic.publish(EventNotifyData.newRegisterDefaultInstance(to, url));
	}
}
