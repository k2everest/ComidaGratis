package br.com.cafebinario.funcional;

@FunctionalInterface
public interface ABConsumer<A, B> {
	
	void accept(final A a,final B b);

}
