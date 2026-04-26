package com.cg.controller;

import com.cg.dto.BookReviewDto;
import com.cg.service.BookReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book-reviews")
public class BookReviewController {
    private final BookReviewService service;
    public BookReviewController(BookReviewService service){ this.service=service; }
    @GetMapping public List<BookReviewDto> all(){ return service.getAll(); }
    @PostMapping public BookReviewDto create(@RequestBody BookReviewDto dto){ return service.save(dto); }
    @DeleteMapping public void delete(@RequestParam String isbn, @RequestParam Integer reviewerId){ service.delete(isbn, reviewerId); }
}
