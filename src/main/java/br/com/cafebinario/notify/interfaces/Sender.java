package br.com.cafebinario.notify.interfaces;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.notify.data.EventNotifyData;

public interface Sender {
	void execute(final EventNotifyData eventNotifyData) throws NotifyException;
}
