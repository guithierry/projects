package com.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.dtos.UserDto;
import com.backend.dtos.response.UserResponseDto;
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
	public UserResponseDto create(UserDto userDto) {

		Optional<User> findByEmail = this.userRepository.findByEmail(userDto.getEmail());

		if (findByEmail.isPresent()) {
			throw new UserAlreadyExistsException("User email already exists");
		}

		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());

		String password = this.authenticationService.hashPassword(userDto.getPassword());
		user.setPassword(password);

		User entity = this.userRepository.save(user);

		return new UserResponseDto(entity);
	}

	public List<UserResponseDto> getAll() {
		return this.userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
	}

	public List<UserResponseDto> getProjectUsers(UUID id) {
		return this.userRepository.findByProjects_Id(id).stream().map(UserResponseDto::new)
				.collect(Collectors.toList());
	}

}
