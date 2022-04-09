package com.backend.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.backend.dtos.UserDto;
import com.backend.entities.User;
import com.backend.exceptions.UserAlreadyExistsException;
import com.backend.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public User create(UserDto userDto) {

		Optional<User> findByEmail = this.userRepository.findByEmail(userDto.getEmail());

		if (findByEmail.isPresent()) {
			throw new UserAlreadyExistsException("User already exists");
		}

		User user = new User();
		BeanUtils.copyProperties(userDto, user);

		return this.userRepository.save(user);
	}

	public List<User> getAll() {
		return this.userRepository.findAll();
	}
}
