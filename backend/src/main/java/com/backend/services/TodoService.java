package com.backend.services;

import java.util.List;
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
	public Todo create(TodoDto todoDto)  {
		Project project = this.projectRepository.findById(UUID.fromString(todoDto.getProjectId()))
				.orElseThrow(() -> new NotFoundException("Project not found."));

		Todo todo = new Todo();
		BeanUtils.copyProperties(todoDto, todo);

		todo.setProject(project);
		project.getTodos().add(todo);

		return todo;
	}

	public Todo read(String id) {
		return this.todoRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Todo not found."));
	}

	public void delete(String id) {
		this.todoRepository.deleteById(UUID.fromString(id));
	}

	@Transactional
	public Todo updateStatus(String id, TodoStatusDto todoStatusDto) {
		Todo todo = this.todoRepository.findById(UUID.fromString(id))
				.orElseThrow(() -> new NotFoundException("Todo not found."));

		todo.setStatus(Status.fromString(todoStatusDto.getStatus()));

		return todo;
	}

	public List<Todo> getTodos(String id) {
		return this.todoRepository.findByProjectId(UUID.fromString(id));
	}
}
