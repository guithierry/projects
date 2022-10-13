package com.backend.services.notifications;

import com.backend.dtos.NotificationDto;

public interface NotificationCreator {
	void create(NotificationDto notificationDto);
}
