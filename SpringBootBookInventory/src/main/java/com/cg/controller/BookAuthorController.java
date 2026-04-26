package com.cg.controller;

import com.cg.dto.BookAuthorDto;
import com.cg.service.BookAuthorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book-authors")
public class BookAuthorController {
    private final BookAuthorService service;
    public BookAuthorController(BookAuthorService service){ this.service=service; }
    @GetMapping public List<BookAuthorDto> all(){ return service.getAll(); }
    @PostMapping public BookAuthorDto create(@RequestBody BookAuthorDto dto){ return service.save(dto); }
    @DeleteMapping public void delete(@RequestParam String isbn, @RequestParam Integer authorID){ service.delete(isbn, authorID); }
}
