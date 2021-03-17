package com.test.autorestdocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class AutoRestDocsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoRestDocsApplication.class, args);
	}

}
