package com.backend.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID>, PagingAndSortingRepository<Comment, UUID> {
	Page<Comment> findByProjectId(Pageable pageable, UUID id);
}
