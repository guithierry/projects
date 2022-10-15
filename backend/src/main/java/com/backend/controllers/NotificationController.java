package com.backend.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.MarkNotificationAsReadDto;
import com.backend.services.notifications.NotificationService;

@RestController
@RequestMapping(path = "/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@PutMapping
	public ResponseEntity<Object> markAllAsRead(@RequestBody MarkNotificationAsReadDto markNotificationAsReadDto) {
		UUID id = markNotificationAsReadDto.getId();
		this.notificationService.markAllAsRead(id);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
