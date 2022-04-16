package com.backend.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.AuthenticationDto;
import com.backend.entities.User;
import com.backend.repositories.UserRepository;
import com.backend.services.AuthenticationService;

@RestController
@RequestMapping(path = "/authentication")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private AuthenticationService authenticationService;
	private UserRepository userRepository;

	public AuthenticationController(AuthenticationManager authenticationManager, 
			AuthenticationService authenticationService, UserRepository userRepository) {
		this.authenticationManager = authenticationManager;
		this.authenticationService = authenticationService;
		this.userRepository = userRepository;
	}
 
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody @Valid AuthenticationDto authenticationDto) {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				authenticationDto.getEmail(), authenticationDto.getPassword());

		try {
			Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			String token = this.authenticationService.generateToken(authenticate);
			User user = this.userRepository.findByEmail(authenticationDto.getEmail()).get();
			
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("token", token);
			response.put("user", user);
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (AuthenticationException exception) {
			Map<String, String> response = new HashMap<String, String>();
			response.put("message", exception.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}
