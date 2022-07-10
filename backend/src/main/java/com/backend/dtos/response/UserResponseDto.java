package com.backend.dtos.response;

import com.backend.entities.User;

public class UserResponseDto extends BasicResponseDto<User> {

	private String name;
	private String email;
	private String picture;

	public UserResponseDto(User entity) {
		super(entity);
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.picture = entity.getPicture();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
