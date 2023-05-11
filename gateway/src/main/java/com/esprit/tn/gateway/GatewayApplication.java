package com.esprit.tn.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@Configuration
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
	public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http
		        .authorizeExchange().pathMatchers("/api/users/register","/oauth2/**").permitAll()
		        .and().authorizeExchange().anyExchange().authenticated()
		        .and()
		        .csrf().disable()
		        .build();
	}

}
