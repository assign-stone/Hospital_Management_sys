package com.cg.hms.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmsAppointmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsAppointmentServiceApplication.class, args);
		System.out.println("Appointment service running on port 8082.");
	}

}
