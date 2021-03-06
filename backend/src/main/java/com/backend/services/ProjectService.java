package com.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.ProjectDto;
import com.backend.dtos.ProjectStatusDto;
import com.backend.dtos.response.ProjectResponseDto;
import com.backend.dtos.response.UserResponseDto;
import com.backend.entities.Project;
import com.backend.entities.User;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.ProjectRepository;
import com.backend.repositories.UserRepository;

@Service
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final UserRepository userRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public ProjectResponseDto create(ProjectDto projectDto) {

		User owner = this.userRepository.findById(UUID.fromString(projectDto.getOwnerId())).get();

		Project project = new Project();
		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		project.setOwner(owner);

		List<User> users = new ArrayList<User>();
		users.add(owner);

		projectDto.getUsers().forEach(u -> {
			User user = this.userRepository.findById(u.getId()).get();
			users.add(user);
		});

		project.setUsers(users);

		Project entity = this.projectRepository.save(project);

		ProjectResponseDto projectResponseDto = new ProjectResponseDto(entity);
		projectResponseDto.setOwner(new UserResponseDto(entity.getOwner()));

		return projectResponseDto;
	}

	public ProjectResponseDto read(UUID id) {
		Optional<Project> project = this.projectRepository.findById(id);

		if (!project.isPresent()) {
			throw new NotFoundException("Project not found");
		}

		ProjectResponseDto projectResponseDto = new ProjectResponseDto(project.get());
		projectResponseDto.setOwner(new UserResponseDto(project.get().getOwner()));

		return projectResponseDto;
	}

	@Transactional
	public void update(UUID id, ProjectDto projectDto) {
		Optional<Project> findProject = this.projectRepository.findById(id);

		if (!findProject.isPresent()) {
			throw new NotFoundException("Project not found");
		}

		Project project = findProject.get();

		if (projectDto.getName() != null) {
			project.setName(projectDto.getName());
		}

		if (projectDto.getDescription() != null) {
			project.setDescription(projectDto.getDescription());
		}
	}

	@Transactional
	public void updateStatus(UUID id, ProjectStatusDto projectStatusDto) {
		Optional<Project> project = this.projectRepository.findById(id);

		if (!project.isPresent()) {
			throw new NotFoundException("Project not found");
		}

		project.get().setStatus(projectStatusDto.getStatus());
	}

	@Transactional
	public void delete(UUID id) {
		this.projectRepository.deleteById(id);
	};

	public List<ProjectResponseDto> getProjects() {

		return this.projectRepository.findAll().stream().map(project -> {
			ProjectResponseDto projectResponseDto = new ProjectResponseDto(project);
			projectResponseDto.setOwner(new UserResponseDto(project.getOwner()));

			return projectResponseDto;
		}).collect(Collectors.toList());
	}
}
