package com.esprit.tn.orderprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import feign.RequestInterceptor;

@EnableFeignClients
@Configuration
@SpringBootApplication
public class OrderProcessingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingServiceApplication.class, args);
	}
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Authorization",
					"Bearer " + ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTokenValue());
		};
	}

}
