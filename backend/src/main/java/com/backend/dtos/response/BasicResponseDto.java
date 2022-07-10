package com.backend.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.backend.entities.BasicEntity;

public abstract class BasicResponseDto<E extends BasicEntity> {

	private UUID id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public BasicResponseDto(E entity) {
		this.id = entity.getId();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
