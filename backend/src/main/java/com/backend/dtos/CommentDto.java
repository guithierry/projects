package com.backend.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentDto {

	@NotNull
	@NotBlank
	private String description;
	
	@NotNull
	@NotBlank
	private String projectId;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
