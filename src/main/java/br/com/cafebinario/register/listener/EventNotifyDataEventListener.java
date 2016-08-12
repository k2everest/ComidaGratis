package br.com.cafebinario.register.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.interfaces.Sender;

@Service
public class EventNotifyDataEventListener implements MessageListener<EventNotifyData> {

	@Autowired
	private Sender sender;

	@Override
	public void onMessage(Message<EventNotifyData> message) {
		sender.execute(message.getMessageObject());
	}
}
