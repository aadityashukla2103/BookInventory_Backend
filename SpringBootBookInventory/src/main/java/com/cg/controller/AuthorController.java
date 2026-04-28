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

	@GetMapping("/count")
	public long count() {
		return authorService.getAll().size();
	}

	@GetMapping("/empty")
	public boolean isEmpty() {
		return authorService.getAll().isEmpty();
	}

	@GetMapping("/first")
	public ResponseEntity<AuthorDto> getFirst() {
		return authorService.getAll().stream().findFirst().map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@GetMapping("/last")
	public ResponseEntity<AuthorDto> getLast() {
		List<AuthorDto> items = authorService.getAll();
		if (items.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(items.get(items.size() - 1));
	}

	@GetMapping("/exists/{authorId}")
	public boolean exists(@PathVariable Integer authorId) {
		try {
			authorService.getById(authorId);
			return true;
		} catch (ResourceNotFoundException ex) {
			return false;
		}
	}

}