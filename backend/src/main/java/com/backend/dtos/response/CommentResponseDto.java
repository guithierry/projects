package com.backend.dtos.response;

import com.backend.entities.Comment;

public class CommentResponseDto extends BasicResponseDto<Comment> {

	private String description;
	private UserResponseDto user;

	public CommentResponseDto(Comment entity) {
		super(entity);
		this.description = entity.getDescription();
		this.user = new UserResponseDto(entity.getUser());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserResponseDto getUser() {
		return user;
	}

	public void setUser(UserResponseDto user) {
		this.user = user;
	}
}
