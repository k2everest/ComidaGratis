package br.com.cafebinario.util;

public class HTMLUtil {
	public static String packingBody(final String body) {
		return String.format("<html><body>%s</body></html>", body);
	}
}
