package com.backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entities.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
	List<Todo> findByProjectId(UUID id);
}
