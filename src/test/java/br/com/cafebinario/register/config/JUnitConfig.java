package br.com.cafebinario.register.config;
import javax.validation.Validator;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class JUnitConfig {

	@Bean
	TestRestTemplate testRestTemplate(){
		return new TestRestTemplate();
	}
	
	@Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
