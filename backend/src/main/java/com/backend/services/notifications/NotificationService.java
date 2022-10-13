package com.backend.services.notifications;

import org.springframework.stereotype.Service;

import com.backend.dtos.NotificationDto;

@Service
public class NotificationService {

	public void create(NotificationCreator notificationCreator, NotificationDto notificationDto) {
		notificationCreator.create(notificationDto);
	}

}
