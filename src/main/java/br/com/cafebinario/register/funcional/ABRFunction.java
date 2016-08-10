package br.com.cafebinario.register.funcional;

@FunctionalInterface
public interface ABRFunction<A, B, R> {
	public R apply(A a, B b);
}
