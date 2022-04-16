package com.backend.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.repositories.UserRepository;
import com.backend.services.AuthenticationService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final AuthenticationService authenticationService;
	private final UserRepository userRepository;
	
	public SecurityConfiguration(AuthenticationService authenticationService, UserRepository userRepository) {
		this.authenticationService = authenticationService;
		this.userRepository = userRepository;
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.authenticationService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
		.and()
		.authorizeRequests()
		
			.antMatchers(HttpMethod.POST, "/authentication").permitAll()
			.antMatchers(HttpMethod.POST, "/users").permitAll()
			.antMatchers(HttpMethod.GET, "/projects").permitAll()
			
			.anyRequest().authenticated().and().csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy((SessionCreationPolicy.STATELESS))
			.and()
			.addFilterBefore(
					new AuthenticationFilter(authenticationService, userRepository),
					UsernamePasswordAuthenticationFilter.class);
	}
}
