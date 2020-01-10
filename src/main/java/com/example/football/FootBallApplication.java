package com.example.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FootBallApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(FootBallApplication.class, args);
	}
	
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	
	
}
