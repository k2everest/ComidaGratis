package br.com.cafebinario.register.funcional;

@FunctionalInterface
public interface ABConsumer<A, B> {
	
	void accept(A a,B b);

}
