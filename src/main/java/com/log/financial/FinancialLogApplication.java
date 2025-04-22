package com.log.financial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinancialLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialLogApplication.class, args);
	}

}
