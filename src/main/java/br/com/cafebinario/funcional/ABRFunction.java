package br.com.cafebinario.funcional;

@FunctionalInterface
public interface ABRFunction<A, B, R> {
	public R apply(final A a, final B b);
}
