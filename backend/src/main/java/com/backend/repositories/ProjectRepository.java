package com.backend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
	List<Project> findByUsers_Id(UUID id);
}
