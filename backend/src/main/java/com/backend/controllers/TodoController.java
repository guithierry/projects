package com.backend.controllers;

import java.util.List;

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
import com.backend.entities.Todo;
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
		Todo todo = this.todoService.create(todoDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(todo);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Object> read(@PathVariable(name = "id") String id) {
		Todo todo = this.todoService.read(id);

		return ResponseEntity.status(HttpStatus.OK).body(todo);
	}

	@PatchMapping(path = "/{id}")
	public ResponseEntity<Object> updateStatus(@PathVariable(name = "id") String id,
			@RequestBody TodoStatusDto todoStatusDto) {
		Todo todo = this.todoService.updateStatus(id, todoStatusDto);

		return ResponseEntity.status(HttpStatus.OK).body(todo);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {
		this.todoService.delete(id);

		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/project/{id}")
	public ResponseEntity<Object> getTodos(@PathVariable(name = "id") String id) {
		List<Todo> todos = this.todoService.getTodos(id);

		return ResponseEntity.status(HttpStatus.OK).body(todos);
	}
}
