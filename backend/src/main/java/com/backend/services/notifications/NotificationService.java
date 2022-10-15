package com.backend.services.notifications;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dtos.NotificationDto;
import com.backend.dtos.response.NotificationResponseDto;
import com.backend.repositories.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	public void create(NotificationCreator notificationCreator, NotificationDto notificationDto) {
		notificationCreator.create(notificationDto);
	}

	public List<NotificationResponseDto> getUserNotifications(UUID id) {
		return this.notificationRepository.findByUsers_id(id).stream().map(NotificationResponseDto::new)
				.collect(Collectors.toList());
	}

	public void markAllAsRead(UUID id) {
		this.notificationRepository.findByUsers_id(id).stream()
				.forEach(((notification) -> {
					notification.setStatus(true);
					this.notificationRepository.save(notification);
				}));
	}

}
