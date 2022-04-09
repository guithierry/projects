package com.backend.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class ProjectDto {
	@NotBlank(message = "Name is required")
	@Length(max = 100, message = "Name is higher than 100 caracteres")
	private String name;
	
	@NotBlank(message = "Description is required")
	@Length(max = 150, message = "Description is higher than 150 caracteres")
	private String description;
	
	private List<UserIdDto> users = new ArrayList<>();
	
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
	
	public List<UserIdDto> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserIdDto> users) {
		this.users = users;
	}
}
