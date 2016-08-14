package br.com.cafebinario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.cafebinario.interceptor.SecureKeyHandlerInterceptor;

@Configuration
@ComponentScan("br.com.cafebinario")
public class CafebinarioIntecepterConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private SecureKeyHandlerInterceptor secureKeyHandlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(secureKeyHandlerInterceptor).excludePathPatterns("/domain*");
		registry.addInterceptor(secureKeyHandlerInterceptor).excludePathPatterns("/user*");
		registry.addInterceptor(secureKeyHandlerInterceptor).addPathPatterns("*");
	}
}
