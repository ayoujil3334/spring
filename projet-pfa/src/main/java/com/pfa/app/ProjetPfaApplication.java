package com.pfa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjetPfaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetPfaApplication.class, args);
	}

}
