package com.cg.controller;

import com.cg.dto.AuthorDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping
	public List<AuthorDto> getAll() {
		return authorService.getAll();
	}

	@GetMapping("/{authorId}")
	public AuthorDto getById(@PathVariable Integer authorId) {
		return authorService.getById(authorId);
	}

	@PostMapping
	public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(dto));
	}

	@PutMapping("/{authorId}")
	public AuthorDto update(@PathVariable Integer authorId, @RequestBody AuthorDto dto) {
		return authorService.update(authorId, dto);
	}

	@DeleteMapping("/{authorId}")
	public ResponseEntity<Void> delete(@PathVariable Integer authorId) {
		authorService.delete(authorId);
		return ResponseEntity.noContent().build();
	}

}