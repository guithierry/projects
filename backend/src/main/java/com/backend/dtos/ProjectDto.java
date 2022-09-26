package com.backend.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class ProjectDto {
	@NotBlank(message = "Name is required")
	@Length(max = 100, message = "Name is higher than 100 caracteres")
	private String name;
	
	@NotBlank(message = "Description is required")
	@Length(max = 150, message = "Description is higher than 150 caracteres")
	private String description;
	
	@NotBlank(message = "Owner id is required")
	@NotNull(message = "Owner id is required")
	private String ownerId; 
	
	private List<UUID> users = new ArrayList<>();
	
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
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	public List<UUID> getUsers() {
		return users;
	}
	
	public void setUsers(List<UUID> users) {
		this.users = users;
	}
}
