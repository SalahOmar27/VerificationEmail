package com.salah.emailVerfication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class UserRegistrationSecurityConfig {
	
	  @Bean
	    public PasswordEncoder passwordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http.cors()
	                .and().csrf().disable()
	                .authorizeHttpRequests()
	                .requestMatchers("/register/**")
	                .permitAll()
	                .and()
	                .authorizeHttpRequests()
	                .requestMatchers("/users/**")
	                .hasAnyAuthority("USER", "ADMIN")
	                .and().formLogin().and().build();
	    }

	    
//	    @Bean
//	    public AuthenticationProvider authenticationProvider(){
//	        var authenticationProvider = new DaoAuthenticationProvider();
//	        authenticationProvider.setUserDetailsService(securityUserDetailsService);
//	        authenticationProvider.setPasswordEncoder(passwordEncoder());
//	       return authenticationProvider;
//	    }
//	    
//	    
//	    @Bean
//	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//	        return authConfig.getAuthenticationManager();
//	    }

}
