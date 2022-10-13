package com.backend.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.CommentDto;
import com.backend.dtos.response.CommentResponseDto;
import com.backend.services.CommentService;

@RestController
@RequestMapping(path = "/comments")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody CommentDto commentDto) {
		CommentResponseDto comment = this.commentService.create(commentDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(comment);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> getAll(@RequestParam("page") Integer page, @PathVariable("id") UUID id) {
		Page<CommentResponseDto> comments = this.commentService.getComments(page, id);

		return ResponseEntity.status(HttpStatus.OK).body(comments);
	}
}
