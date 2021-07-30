package com.bside.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	static {
		System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
