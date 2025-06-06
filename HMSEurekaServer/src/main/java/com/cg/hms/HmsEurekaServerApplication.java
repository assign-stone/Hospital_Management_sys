package com.cg.hms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HmsEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsEurekaServerApplication.class, args);
		System.out.println("Eureka started on 8761");
	}

}
