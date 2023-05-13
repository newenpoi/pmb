package com.openclassrooms.newenpoi.pmb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/payments", "/contacts", "/register/**", "register/validate", "/profile", "/css/**").permitAll().anyRequest().authenticated())
	        .formLogin(formLogin -> formLogin.loginPage("/user-login").usernameParameter("email").loginProcessingUrl("/login").defaultSuccessUrl("/payments").failureUrl("/user-login/failure").permitAll())
	        .build();
	}
}
