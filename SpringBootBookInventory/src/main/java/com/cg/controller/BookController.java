package com.cg.controller;

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
    public ResponseEntity<BookDto> create(@RequestBody BookDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(dto));
    }

    @PutMapping("/{isbn}")
    public BookDto update(@PathVariable String isbn, @RequestBody BookDto dto) {
        return bookService.update(isbn, dto);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable String isbn) {
        bookService.delete(isbn);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    public long count() {
        return bookService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return bookService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<BookDto> getFirst() {
        return bookService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    

    @GetMapping("/last")
    public ResponseEntity<BookDto> getLast() {
        List<BookDto> items = bookService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    
    @GetMapping("/exists/{isbn}")
    public boolean exists(@PathVariable String isbn) {
        try {
            bookService.getById(isbn);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}