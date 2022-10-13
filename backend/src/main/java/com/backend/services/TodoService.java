package com.backend.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.dtos.NotificationDto;
import com.backend.dtos.TodoDto;
import com.backend.dtos.TodoStatusDto;
import com.backend.dtos.response.TodoResponseDto;
import com.backend.entities.NotificationType;
import com.backend.entities.Project;
import com.backend.entities.Status;
import com.backend.entities.Todo;
import com.backend.entities.User;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.ProjectRepository;
import com.backend.repositories.TodoRepository;
import com.backend.services.notifications.NotificationService;
import com.backend.services.notifications.TodoNotificationService;

@Service
public class TodoService {

	private TodoRepository todoRepository;
	private ProjectRepository projectRepository;
	private NotificationService notificationService;
	private TodoNotificationService todoNotificationService;

	public TodoService(TodoRepository todoRepository, ProjectRepository projectRepository,
			NotificationService notificationService, TodoNotificationService todoNotificationService) {
		this.todoRepository = todoRepository;
		this.projectRepository = projectRepository;
		this.notificationService = notificationService;
		this.todoNotificationService = todoNotificationService;
	}

	@Transactional
	public TodoResponseDto create(TodoDto todoDto) {
		Optional<Project> findProject = this.projectRepository.findById(todoDto.getProjectId());

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
		Todo todo = this.todoRepository.findById(id).get();

		todo.setStatus(Status.fromString(todoStatusDto.getStatus()));

		if (todo.getStatus().equals(Status.DONE) && todo.getAssigned() != null) {

			User owner = todo.getProject().getOwner();
			User assigned = todo.getAssigned();

			List<User> users = Arrays.asList(owner, assigned);

			this.notificationService.create(this.todoNotificationService, new NotificationDto(
					NotificationType.TODO_NOTIFICATION, "Notification message when todo is done", users, null, todo));
		}
	}

	public List<TodoResponseDto> getTodos(UUID id) {
		return this.todoRepository.findByProjectId(id).stream().map(TodoResponseDto::new).collect(Collectors.toList());
	}
}
