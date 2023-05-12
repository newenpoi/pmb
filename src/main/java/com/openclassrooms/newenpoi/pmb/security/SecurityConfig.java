package com.openclassrooms.newenpoi.pmb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/payments", "/contacts", "/register", "/profile", "/css/**").permitAll().anyRequest().authenticated())
	        .formLogin(formLogin -> formLogin.loginPage("/user-login").permitAll())
	        .build();
	}
}
