package com.cg.hms.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmsuiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsuiServiceApplication.class, args);
		System.out.println("UI service running on port 8085..");
	}

}
