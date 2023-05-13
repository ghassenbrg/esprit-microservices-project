package com.esprit.tn.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import feign.RequestInterceptor;
/* communication d'inventory */
@EnableFeignClients
@Configuration
@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Authorization",
					"Bearer " + ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTokenValue());
		};
	}
}
