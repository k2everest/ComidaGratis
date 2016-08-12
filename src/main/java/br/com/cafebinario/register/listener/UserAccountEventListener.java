package br.com.cafebinario.register.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import br.com.cafebinario.entiry.UserAccount;
import br.com.cafebinario.register.rules.user.PersistUserRules;

@Service
public class UserAccountEventListener implements MessageListener<UserAccount>  {

	@Autowired
	private PersistUserRules persistUserRules;

	@Transactional
	public void onMessage(Message<UserAccount> message) {
		persistUserRules.accept(message.getMessageObject());
	}
}