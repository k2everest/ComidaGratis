package br.com.cafebinario.exception;

import java.util.List;

public class SendEmailException extends NotifyException{

	private static final long serialVersionUID = 8222529993991201042L;
	private final List<Exception> errors;
	

	public SendEmailException(final String message, final List<Exception> errors) {
		super(message);
		this.errors = errors;
	}

	public List<Exception> getErrors() {
		return errors;
	}
}
