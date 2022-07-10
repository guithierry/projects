package com.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.TodoDto;
import com.backend.dtos.TodoStatusDto;
import com.backend.dtos.response.TodoResponseDto;
import com.backend.entities.Project;
import com.backend.entities.Status;
import com.backend.entities.Todo;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.ProjectRepository;
import com.backend.repositories.TodoRepository;

@Service
public class TodoService {

	private final TodoRepository todoRepository;
	private final ProjectRepository projectRepository;

	@Autowired
	public TodoService(TodoRepository todoRepository, ProjectRepository projectRepository) {
		this.todoRepository = todoRepository;
		this.projectRepository = projectRepository;
	}

	@Transactional
	public TodoResponseDto create(TodoDto todoDto) {
		Optional<Project> findProject = this.projectRepository.findById(UUID.fromString(todoDto.getProjectId()));

		if (!findProject.isPresent()) {
			throw new NotFoundException("Project not found");
		}

		Project project = findProject.get();

		Todo todo = new Todo();
		todo.setName(todoDto.getName());
		todo.setDescription(todoDto.getDescription());
		todo.setProject(project);

		Todo entity = this.todoRepository.save(todo);

		return new TodoResponseDto(entity);
	}

	public TodoResponseDto read(UUID id) {
		Optional<Todo> todo = this.todoRepository.findById(id);

		if (!todo.isPresent()) {
			throw new NotFoundException("Todo not found");
		}

		return new TodoResponseDto(todo.get());
	}

	@Transactional
	public void delete(UUID id) {
		this.todoRepository.deleteById(id);
	}

	@Transactional
	public void updateStatus(UUID id, TodoStatusDto todoStatusDto) {
		Optional<Todo> todo = this.todoRepository.findById(id);

		if (!todo.isPresent()) {
			throw new NotFoundException("Todo not found");
		}

		todo.get().setStatus(Status.fromString(todoStatusDto.getStatus()));
	}

	public List<TodoResponseDto> getTodos(UUID id) {
		return this.todoRepository.findByProjectId(id).stream().map(TodoResponseDto::new).collect(Collectors.toList());
	}
}
