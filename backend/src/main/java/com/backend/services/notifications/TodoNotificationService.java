package com.backend.services.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.NotificationDto;
import com.backend.entities.Notification;
import com.backend.repositories.NotificationRepository;

@Service
public class TodoNotificationService implements NotificationCreator {

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public void create(NotificationDto notificationDto) {

		Notification notification = new Notification();
		notification.setMessage(notificationDto.getMessage());
		notification.setUsers(notificationDto.getUsers());
		notification.setNotificationType(notificationDto.getNotificationType());
		notification.setTodo(notification.getTodo());

		this.notificationRepository.save(notification);
	}

}
