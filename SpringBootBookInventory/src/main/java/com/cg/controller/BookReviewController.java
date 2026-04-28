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


    @GetMapping("/count")
    public long count() {
        return bookReviewService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return bookReviewService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<BookReviewDto> getFirst() {
        return bookReviewService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    

    @GetMapping("/last")
    public ResponseEntity<BookReviewDto> getLast() {
        List<BookReviewDto> items = bookReviewService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    @GetMapping("/exists")
    public boolean exists(@RequestParam String isbn, @RequestParam Integer reviewerId) {
        try {
            bookReviewService.getById(isbn, reviewerId);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}