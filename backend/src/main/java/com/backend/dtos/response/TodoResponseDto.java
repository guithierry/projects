package com.backend.dtos.response;

import com.backend.entities.Status;
import com.backend.entities.Todo;

public class TodoResponseDto extends BasicResponseDto<Todo> {

	private String name;
	private String description;
	private Status status;
	private UserResponseDto assigned;

	public TodoResponseDto(Todo entity) {
		super(entity);
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.status = entity.getStatus();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public UserResponseDto getAssigned() {
		return assigned;
	}

	public void setAssigned(UserResponseDto assigned) {
		this.assigned = assigned;
	}
}
