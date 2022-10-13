package com.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.UserDto;
import com.backend.dtos.response.UserResponseDto;
import com.backend.services.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody UserDto userDto) {
		UserResponseDto user = this.userService.create(userDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@GetMapping
	public ResponseEntity<Object> getAll() {
		List<UserResponseDto> users = this.userService.getAll();

		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
}
