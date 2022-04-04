package com.backend.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.ProjectDto;
import com.backend.dtos.ProjectStatusDto;
import com.backend.entities.Project;
import com.backend.services.ProjectService;

@RestController
@RequestMapping(path = "/projects")
public class ProjectController {

	private final ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ProjectDto projectDto) {
		Project project = projectService.create(projectDto);

		return ResponseEntity.status(HttpStatus.OK).body(project);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> read(@PathVariable(name = "id") String id) {
		Project project = this.projectService.read(id);

		return ResponseEntity.status(HttpStatus.OK).body(project);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> update(@PathVariable(name = "id") String id, @RequestBody ProjectDto projectDto) {
		Project project = this.projectService.update(id, projectDto);

		return ResponseEntity.status(HttpStatus.OK).body(project);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<Object> updateStatus(@PathVariable(name = "id") String id,
			@RequestBody ProjectStatusDto projectStatusDto) {
		Project project = this.projectService.updateStatus(id, projectStatusDto);

		return ResponseEntity.status(HttpStatus.OK).body(project);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {
		this.projectService.delete(id);

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Object> getProjects() {
		List<Project> projects = this.projectService.getProjects();

		return ResponseEntity.status(HttpStatus.OK).body(projects);
	}
}
