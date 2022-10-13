package com.backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
