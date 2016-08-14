package br.com.cafebinario.register.listener;

import org.springframework.stereotype.Component;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import br.com.cafebinario.entity.DomainAccount;

@Component
public class ConfigPartitionDomainAccountMessageListener implements MessageListener<DomainAccount>{

	@Override
	public void onMessage(Message<DomainAccount> message) {
		System.out.println(message.getMessageObject());
	}
}
