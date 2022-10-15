package com.backend.dtos;

import java.util.UUID;

public class MarkNotificationAsReadDto {

	private UUID id;

	public MarkNotificationAsReadDto() {
	}
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
