package com.backend.dtos.response;

import com.backend.entities.Notification;

public abstract class NotificationResponseDto extends BasicResponseDto<Notification> {

	private String message;

	public NotificationResponseDto(Notification entity) {
		super(entity);
		this.message = entity.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
