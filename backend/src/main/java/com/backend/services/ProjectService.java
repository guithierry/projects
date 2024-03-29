package com.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

	private ProjectRepository projectRepository;
	private UserRepository userRepository;

	public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public ProjectResponseDto create(ProjectDto projectDto) {

		User owner = this.userRepository.findById(projectDto.getOwnerId()).get();

		Project project = new Project();
		project.setName(projectDto.getName());
		project.setDescription(projectDto.getDescription());
		project.setOwner(owner);

		List<User> users = new ArrayList<User>();
		users.add(owner);

		if (!projectDto.getUsers().isEmpty()) {
			projectDto.getUsers().stream().forEach((id) -> {
				User user = this.userRepository.findById(id).get();
				users.add(user);
			});
		}

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

		List<UserResponseDto> users = project.get().getUsers().stream().map(UserResponseDto::new)
				.collect(Collectors.toList());
		projectResponseDto.setUsers(users);

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

	public List<ProjectResponseDto> getProjects() {

		return this.projectRepository.findAll().stream().map(project -> {
			ProjectResponseDto projectResponseDto = new ProjectResponseDto(project);
			projectResponseDto.setOwner(new UserResponseDto(project.getOwner()));

			List<UserResponseDto> users = project.getUsers().stream().map(UserResponseDto::new).toList();
			projectResponseDto.setUsers(users);

			return projectResponseDto;
		}).collect(Collectors.toList());
	}

	public List<ProjectResponseDto> getUserProjects(UUID id) {
		return this.projectRepository.findByUsers_Id(id).stream().map((project) -> {
			ProjectResponseDto projectResponseDto = new ProjectResponseDto(project);
			projectResponseDto.setOwner(new UserResponseDto(project.getOwner()));

			List<UserResponseDto> users = project.getUsers().stream().map(UserResponseDto::new).toList();
			projectResponseDto.setUsers(users);

			return projectResponseDto;
		}).collect(Collectors.toList());
	}
}
