package com.backend.dtos.response;

import com.backend.entities.Notification;
import com.backend.entities.NotificationType;

public class NotificationResponseDto extends BasicResponseDto<Notification> {

	private String message;
	private NotificationType notificationType;
	private Boolean status;

	public NotificationResponseDto(Notification entity) {
		super(entity);
		this.message = entity.getMessage();
		this.notificationType = entity.getNotificationType();
		this.status = entity.getStatus();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
