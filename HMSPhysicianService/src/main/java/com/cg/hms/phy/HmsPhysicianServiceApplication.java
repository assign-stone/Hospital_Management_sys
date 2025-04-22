package com.cg.hms.phy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmsPhysicianServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsPhysicianServiceApplication.class, args);
		System.out.println("Application is running on 8080 port");
	}

}
