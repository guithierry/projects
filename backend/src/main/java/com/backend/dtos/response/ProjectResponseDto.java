package com.backend.dtos.response;

import java.util.ArrayList;
import java.util.List;

import com.backend.entities.Project;

public class ProjectResponseDto extends BasicResponseDto<Project> {

	private String name;
	private String description;
	private Boolean status;
	private UserResponseDto owner;
	private List<UserResponseDto> users = new ArrayList<UserResponseDto>();

	public ProjectResponseDto(Project project) {
		super(project);
		this.name = project.getName();
		this.description = project.getDescription();
		this.status = project.getStatus();
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public UserResponseDto getOwner() {
		return owner;
	}

	public void setOwner(UserResponseDto owner) {
		this.owner = owner;
	}

	public List<UserResponseDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserResponseDto> users) {
		this.users = users;
	}
}
