package com.cg.controller;

import com.cg.dto.BookAuthorDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.BookAuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-authors")
public class BookAuthorController {
	private final BookAuthorService bookAuthorService;

	public BookAuthorController(BookAuthorService bookAuthorService) {
		this.bookAuthorService = bookAuthorService;
	}

	@GetMapping
	public List<BookAuthorDto> getAll() {
		return bookAuthorService.getAll();
	}

	@GetMapping("/{isbn}/{authorId}")
	public BookAuthorDto getById(@PathVariable String isbn, @PathVariable Integer authorId) {
		return bookAuthorService.getById(isbn, authorId);
	}

	@PostMapping
	public ResponseEntity<BookAuthorDto> create(@RequestBody BookAuthorDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bookAuthorService.create(dto));
	}

	@PutMapping("/{isbn}/{authorId}")
	public BookAuthorDto update(@PathVariable String isbn, @PathVariable Integer authorId,
			@RequestBody BookAuthorDto dto) {
		return bookAuthorService.update(isbn, authorId, dto);
	}

	@DeleteMapping("/{isbn}/{authorId}")
	public ResponseEntity<Void> delete(@PathVariable String isbn, @PathVariable Integer authorId) {
		bookAuthorService.delete(isbn, authorId);
		return ResponseEntity.noContent().build();
	}

}