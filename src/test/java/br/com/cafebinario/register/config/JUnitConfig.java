package br.com.cafebinario.register.config;

import javax.validation.Validator;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.subethamail.wiser.Wiser;

@Configuration
public class JUnitConfig {

	@Bean
	TestRestTemplate testRestTemplate() {
		return new TestRestTemplate();
	}

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	private static Wiser wiser;

	@Bean(destroyMethod = "stop")
	Wiser wiser() {
		if (wiser == null) {
			wiser = new Wiser();
			wiser.setHostname("cafebinario.com.br");
			wiser.setPort(25);
			wiser.start();
		}
		return wiser;
	}
}
