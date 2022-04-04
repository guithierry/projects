package com.backend.services;

import java.util.List;
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

	public Project create(ProjectDto projectDto) {
		Project project = new Project();

		BeanUtils.copyProperties(projectDto, project);

		return this.projectRepository.save(project);
	}

	public Project read(String id) {
		return this.projectRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Project not found"));
	}

	@Transactional
	public Project update(String id, ProjectDto projectDto) {
		Project project = this.projectRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Project not found"));

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
		Project project = this.projectRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Project not found"));

		project.setStatus(projectStatusDto.getStatus());

		return project;
	}

	public void delete(String id) {
		this.projectRepository.deleteById(UUID.fromString(id));
	};

	public List<Project> getProjects() {
		return this.projectRepository.findAll();
	}
}
