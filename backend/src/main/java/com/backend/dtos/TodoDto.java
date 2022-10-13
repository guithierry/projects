package com.backend.dtos;

import java.util.UUID;

public class TodoDto {
	private UUID projectId;
	private String name;
	private String description;

	public UUID getProjectId() {
		return projectId;
	}
	
	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
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
}
