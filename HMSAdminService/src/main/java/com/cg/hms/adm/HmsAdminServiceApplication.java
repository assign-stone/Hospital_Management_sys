package com.cg.hms.adm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmsAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsAdminServiceApplication.class, args);
		System.out.println("Starting Port 8087......");
	}

}
