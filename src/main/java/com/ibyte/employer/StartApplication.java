package com.ibyte.employer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class StartApplication {

	@Profile("teste")
	public static void main(String[] args) {
		// change context-path to '/api' and start application
		System.setProperty("server.servlet.context-path", "/api");
		SpringApplication.run(StartApplication.class, args);
	}

}
