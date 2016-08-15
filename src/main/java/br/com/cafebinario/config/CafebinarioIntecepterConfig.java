package br.com.cafebinario.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.cafebinario.interceptor.SecureKeyHandlerInterceptor;

@Configuration
@ComponentScan("br.com.cafebinario")
@ConfigurationProperties("cafebinario")
public class CafebinarioIntecepterConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private SecureKeyHandlerInterceptor secureKeyHandlerInterceptor;
	
	@Value("${pathPatterns:/**}")
	private String pathPatterns;

	@Value("${excludePathPatterns:/domain/**,/user/**,/api/**}")
	private String excludePathPatterns = "";

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(secureKeyHandlerInterceptor).addPathPatterns(pathPatterns.split(","))
				.excludePathPatterns(excludePathPatterns.split(","));
	}
}