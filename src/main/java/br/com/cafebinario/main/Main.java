package br.com.cafebinario.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("br.com.cafebinario")
@EnableJpaRepositories("br.com.cafebinario")
@EntityScan("br.com.cafebinario")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
