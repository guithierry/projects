package com.backend.services;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.backend.dtos.CommentDto;
import com.backend.entities.Comment;
import com.backend.entities.Project;
import com.backend.repositories.CommentRepository;
import com.backend.repositories.ProjectRepository;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private ProjectRepository projectRepository;

	public CommentService(CommentRepository commentRepository, ProjectRepository projectRepository) {
		this.commentRepository = commentRepository;
		this.projectRepository = projectRepository;
	}

	@Transactional
	public Comment create(CommentDto commentDto) {

		Project project = this.projectRepository.findById(UUID.fromString(commentDto.getProjectId())).get();

		Comment comment = new Comment();
		comment.setDescription(commentDto.getDescription());
		comment.setProject(project);

		project.getComments().add(comment);

		return this.commentRepository.save(comment);
	}

	public Page<Comment> getAll(Integer page, String id) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "createdAt"));
		return this.commentRepository.findByProjectId(pageable, UUID.fromString(id));
	}
}
