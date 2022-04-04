package com.backend.dtos;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class ProjectDto {
	@NotBlank(message = "Name is required")
	@Length(max = 100, message = "Name is higher than 100 caracteres")
	private String name;
	
	@NotBlank(message = "Description is required")
	@Length(max = 150, message = "Description is higher than 150 caracteres")
	private String description;
	
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
	
	@Override
	public String toString() {
		return this.name + " " + this.description;
	}
}
