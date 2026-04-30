package com.cg.controller;
import jakarta.validation.Valid;

import com.cg.dto.BookDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping
    public List<BookDto> getAll() { return bookService.getAll(); }

    @GetMapping("/{isbn}")
    public BookDto getById(@PathVariable String isbn) { 
    	return bookService.getById(isbn);
    	}

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @PutMapping("/{isbn}")
    public BookDto update(@PathVariable String isbn,@Valid @RequestBody BookDto dto) {
        return bookService.update(isbn, dto);
    } 

    @GetMapping("/count")
    public long count() {
        return bookService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return bookService.getAll().isEmpty();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        bookService.delete(isbn);
        return ResponseEntity.noContent().build();
    }

}