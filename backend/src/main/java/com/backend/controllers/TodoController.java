package com.backend.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.TodoDto;
import com.backend.dtos.TodoStatusDto;
import com.backend.dtos.response.TodoResponseDto;
import com.backend.services.TodoService;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {

	private final TodoService todoService;

	@Autowired
	public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody TodoDto todoDto) {
		TodoResponseDto todo = this.todoService.create(todoDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(todo);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> read(@PathVariable(name = "id") UUID id) {
		TodoResponseDto todo = this.todoService.read(id);

		return ResponseEntity.status(HttpStatus.OK).body(todo);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<Object> updateStatus(@PathVariable(name = "id") UUID id,
			@RequestBody TodoStatusDto todoStatusDto) {
		this.todoService.updateStatus(id, todoStatusDto);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id) {
		this.todoService.delete(id);

		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/project/{id}")
	public ResponseEntity<Object> getTodos(@PathVariable(name = "id") UUID id) {
		List<TodoResponseDto> todos = this.todoService.getTodos(id);

		return ResponseEntity.status(HttpStatus.OK).body(todos);
	}
}
