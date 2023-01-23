package com.boeing.rewardcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
public class RewardcalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardcalculatorApplication.class, args);
	}

	// To produce JSON Output in more readable format with indentation
	@Bean
	public ObjectMapper objectMapper() {
	    return new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);
	}
	
}
