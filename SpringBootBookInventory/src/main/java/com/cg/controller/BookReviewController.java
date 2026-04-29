package com.cg.controller;

import com.cg.dto.BookReviewDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.BookReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-reviews")
public class BookReviewController {
    private final BookReviewService bookReviewService;

    public BookReviewController(BookReviewService bookReviewService) { this.bookReviewService = bookReviewService; }

    @GetMapping
    public List<BookReviewDto> getAll() { return bookReviewService.getAll(); }

    @GetMapping("/{isbn}/{reviewerId}")
    public BookReviewDto getById(@PathVariable String isbn, @PathVariable Integer reviewerId) {
        return bookReviewService.getById(isbn, reviewerId);
    }

    @PostMapping
    public ResponseEntity<BookReviewDto> create(@RequestBody BookReviewDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookReviewService.create(dto));
    }

    @PutMapping("/{isbn}/{reviewerId}")
    public BookReviewDto update(@PathVariable String isbn, @PathVariable Integer reviewerId, @RequestBody BookReviewDto dto) {
        return bookReviewService.update(isbn, reviewerId, dto);
    }

    @DeleteMapping("/{isbn}/{reviewerId}")
    public ResponseEntity<Void> delete(@PathVariable String isbn, @PathVariable Integer reviewerId) {
        bookReviewService.delete(isbn, reviewerId);
        return ResponseEntity.noContent().build();
    }

}