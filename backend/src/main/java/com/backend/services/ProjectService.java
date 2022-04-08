package com.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.ProjectDto;
import com.backend.dtos.ProjectStatusDto;
import com.backend.entities.Project;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.ProjectRepository;

@Service
public class ProjectService {

	private final ProjectRepository projectRepository;

	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Transactional
	public Project create(ProjectDto projectDto) {
		Project project = new Project();

		BeanUtils.copyProperties(projectDto, project);

		return this.projectRepository.save(project);
	}

	public Project read(String id) {
		Optional<Project> project = this.projectRepository.findById(UUID.fromString(id));
	
		if (!project.isPresent()) {
			throw new NotFoundException("Project not found");
		}
	
		return project.get();
	}

	@Transactional
	public Project update(String id, ProjectDto projectDto) {
		Optional<Project> findProject = this.projectRepository.findById(UUID.fromString(id));

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

		return project;
	}

	@Transactional
	public Project updateStatus(String id, ProjectStatusDto projectStatusDto) {
		Optional<Project> findProject = this.projectRepository.findById(UUID.fromString(id));

		if (!findProject.isPresent()) {
			throw new NotFoundException("Project not found");
		}

		Project project = findProject.get();
		project.setStatus(projectStatusDto.getStatus());

		return project;
	}

	@Transactional
	public void delete(String id) {
		this.projectRepository.deleteById(UUID.fromString(id));
	};

	public List<Project> getProjects() {
		return this.projectRepository.findAll();
	}
}