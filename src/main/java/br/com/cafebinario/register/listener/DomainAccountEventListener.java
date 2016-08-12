package br.com.cafebinario.register.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import br.com.cafebinario.entiry.DomainAccount;
import br.com.cafebinario.register.rules.domain.PersistDomainRules;

@Service
public class DomainAccountEventListener implements MessageListener<DomainAccount>{

	@Autowired
	private PersistDomainRules persistDomainRules;
	
	@Transactional
	public void onMessage(Message<DomainAccount> message) {
		persistDomainRules.accept(message.getMessageObject());
	}
}