package com.backend.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.dtos.AssignTodoDto;
import com.backend.dtos.NotificationDto;
import com.backend.dtos.TodoDto;
import com.backend.dtos.TodoStatusDto;
import com.backend.dtos.response.TodoResponseDto;
import com.backend.dtos.response.UserResponseDto;
import com.backend.entities.NotificationType;
import com.backend.entities.Project;
import com.backend.entities.Status;
import com.backend.entities.Todo;
import com.backend.entities.User;
import com.backend.exceptions.NotFoundException;
import com.backend.repositories.ProjectRepository;
import com.backend.repositories.TodoRepository;
import com.backend.repositories.UserRepository;
import com.backend.services.notifications.NotificationService;
import com.backend.services.notifications.TodoNotificationService;

@Service
public class TodoService {

	private TodoRepository todoRepository;
	private ProjectRepository projectRepository;
	private NotificationService notificationService;
	private TodoNotificationService todoNotificationService;
	private UserRepository userRepository;

	public TodoService(TodoRepository todoRepository, ProjectRepository projectRepository,
			NotificationService notificationService, TodoNotificationService todoNotificationService,
			UserRepository userRepository) {
		this.todoRepository = todoRepository;
		this.projectRepository = projectRepository;
		this.notificationService = notificationService;
		this.todoNotificationService = todoNotificationService;
		this.userRepository = userRepository;
	}

	@Transactional
	public TodoResponseDto create(TodoDto todoDto) {
		Project project = this.projectRepository.findById(todoDto.getProjectId()).get();

		Todo todo = new Todo();
		todo.setName(todoDto.getName());
		todo.setDescription(todoDto.getDescription());
		todo.setProject(project);

		if (todoDto.getUserId() != null) {
			User user = this.userRepository.findById(todoDto.getUserId()).get();
			todo.setAssigned(user);
		}

		Todo entity = this.todoRepository.save(todo);

		TodoResponseDto todoResponseDto = new TodoResponseDto(entity);
		todoResponseDto.setAssigned(new UserResponseDto(entity.getAssigned()));
		
		return todoResponseDto;
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
		return this.todoRepository.findByProjectId(id).stream().map((todo) -> {
			TodoResponseDto todoResponseDto = new TodoResponseDto(todo);
			
			if (todo.getAssigned() != null) { 
				todoResponseDto.setAssigned(new UserResponseDto(todo.getAssigned()));
			}

			return todoResponseDto;
		}).collect(Collectors.toList());
	}

	public TodoResponseDto assignUser(UUID id, AssignTodoDto assignTodoDto) {
		User user = this.userRepository.findById(assignTodoDto.getUserId()).get();
		Todo todo = this.todoRepository.findById(id).get();

		todo.setAssigned(user);

		Todo entity = this.todoRepository.save(todo);

		TodoResponseDto todoResponseDto = new TodoResponseDto(entity);
		todoResponseDto.setAssigned(new UserResponseDto(todo.getAssigned()));
		
		return todoResponseDto;
	}
}
