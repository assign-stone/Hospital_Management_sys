package com.cg.hms;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HmsGatewayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsGatewayApiApplication.class, args);
		System.out.println(" HMSApiGateway1Application is running on port 8089..");
	}

}
