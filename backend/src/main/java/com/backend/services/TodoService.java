package com.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.TodoDto;
import com.backend.dtos.TodoStatusDto;
import com.backend.entities.Status;
import com.backend.entities.Project;
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
	public Todo create(TodoDto todoDto) {
		Optional<Project> findProject = this.projectRepository.findById(UUID.fromString(todoDto.getProjectId()));

		if (!findProject.isPresent()) {
			throw new NotFoundException("Project not found");
		}

		Project project = findProject.get();

		Todo todo = new Todo();
		BeanUtils.copyProperties(todoDto, todo);
		todo.setProject(project);

		project.getTodos().add(todo);

		return todo;
	}

	public Todo read(String id) {
		Optional<Todo> todo = this.todoRepository.findById(UUID.fromString(id));
		
		if (!todo.isPresent()) {
			throw new NotFoundException("Todo not found");
		}
		
		return todo.get();
	}

	@Transactional
	public void delete(String id) {
		this.todoRepository.deleteById(UUID.fromString(id));
	}

	@Transactional
	public Todo updateStatus(String id, TodoStatusDto todoStatusDto) {
		Optional<Todo> findTodo = this.todoRepository.findById(UUID.fromString(id));
		
		if (!findTodo.isPresent()) {
			throw new NotFoundException("Todo not found");
		}
		
		Todo todo = findTodo.get();
		todo.setStatus(Status.fromString(todoStatusDto.getStatus()));

		return todo;
	}

	public List<Todo> getTodos(String id) {
		return this.todoRepository.findByProjectId(UUID.fromString(id));
	}
}
