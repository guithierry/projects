package com.backend.dtos;

import java.util.UUID;

public class AssignTodoDto {

	private UUID userId;

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

}
