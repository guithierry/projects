package com.backend.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.dtos.UserDto;
import com.backend.entities.User;
import com.backend.exceptions.UserAlreadyExistsException;
import com.backend.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final AuthenticationService authenticationService;

	public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
		this.userRepository = userRepository;
		this.authenticationService = authenticationService;
	}

	@Transactional
	public User create(UserDto userDto) {

		Optional<User> findByEmail = this.userRepository.findByEmail(userDto.getEmail());

		if (findByEmail.isPresent()) {
			throw new UserAlreadyExistsException("User email already exists");
		}

		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		
		String password = this.authenticationService.hashPassword(userDto.getPassword());
		user.setPassword(password);
		
		return this.userRepository.save(user);
	}

	public List<User> getAll() {
		return this.userRepository.findAll();
	}
}
