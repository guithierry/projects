package com.backend.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.backend.dtos.CommentDto;
import com.backend.dtos.response.CommentResponseDto;
import com.backend.dtos.response.UserResponseDto;
import com.backend.entities.Comment;
import com.backend.entities.Project;
import com.backend.entities.User;
import com.backend.repositories.CommentRepository;
import com.backend.repositories.ProjectRepository;
import com.backend.repositories.UserRepository;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private ProjectRepository projectRepository;
	private UserRepository userRepository;

	public CommentService(CommentRepository commentRepository, ProjectRepository projectRepository,
			UserRepository userRepository) {
		this.commentRepository = commentRepository;
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public CommentResponseDto create(CommentDto commentDto) {

		Project project = this.projectRepository.findById(UUID.fromString(commentDto.getProjectId())).get();
		User user = this.userRepository.findById(UUID.fromString(commentDto.getUserId())).get();

		Comment comment = new Comment();
		comment.setDescription(commentDto.getDescription());
		comment.setUser(user);
		comment.setProject(project);

		Comment managed = this.commentRepository.save(comment);

		CommentResponseDto commentResponseDto = new CommentResponseDto(managed);
		commentResponseDto.setUser(new UserResponseDto(managed.getUser()));

		return commentResponseDto;
	}

	public Page<CommentResponseDto> getComments(Integer page, UUID id) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by(Direction.DESC, "createdAt"));

		Page<Comment> comments = this.commentRepository.findByProjectId(pageable, id);

		List<CommentResponseDto> commentsResponseDto = comments.stream().map(comment ->{
			CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
			commentResponseDto.setUser(new UserResponseDto(comment.getUser()));
			
			return commentResponseDto;
		}).collect(Collectors.toList());

		return new PageImpl<CommentResponseDto>(commentsResponseDto, pageable, comments.getTotalElements());
	}
}
