package br.com.cafebinario.exception;

public class NotifyException extends RuntimeException{
	private static final long serialVersionUID = -8359850379808630240L;
	
	public NotifyException(final String message) {
		super(message);
	}
}
