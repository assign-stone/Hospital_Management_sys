package com.cg.hms.ui.config;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UIConfig {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate1() {
		return new RestTemplate();
	}

//	@Bean
//	public RestTemplate restTemplate() {
//		RestTemplate restTemplate = new RestTemplate();
//		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//		interceptors.add((request, body, execution) -> {
//			//String auth = adminServiceConfig.getUsername() + ":" + adminServiceConfig.getPassword();
//			
//			String auth =  "vaishali@example.com"+":" +"vaishali123" ;
//    //BCrypt
//			byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
//			
//			//byte[] encodedAuth = BCrypt.getEncoder().encode(auth.getBytes());
//
//			String authHeader = "Basic " + new String(encodedAuth);
//			request.getHeaders().add("Authorization", authHeader);
//			return execution.execute(request, body);
//		});
//		restTemplate.setInterceptors(interceptors);
//		return restTemplate;
//	}

}
