package com.backend.dtos;

import java.util.ArrayList;
import java.util.List;

import com.backend.entities.NotificationType;
import com.backend.entities.Project;
import com.backend.entities.Todo;
import com.backend.entities.User;

public class NotificationDto {

	private NotificationType notificationType;
	private String message;
	private List<User> users = new ArrayList<User>();
	private Project project;
	private Todo todo;

	/**
	 * 
	 * @param project          = Project id if the notification is a project
	 *                         notification level
	 * @param todo             = Todo id if the notification is a todo notification
	 *                         level
	 * @param notificationType
	 */
	public NotificationDto(NotificationType notificationType, String message, List<User> users, Project project,
			Todo todo) {
		this.notificationType = notificationType;
		this.message = message;
		this.users = users;
		this.project = project;
		this.todo = todo;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}

}
