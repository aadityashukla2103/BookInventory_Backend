package com.cg.controller;

import com.cg.dto.BookDto;
import com.cg.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service) { this.service = service; }
    @GetMapping public List<BookDto> getAll() { return service.getAll(); }
    @GetMapping("/{isbn}") public BookDto getById(@PathVariable String isbn) { return service.getById(isbn); }
    @GetMapping("/search") public List<BookDto> search(@RequestParam String title){ return service.searchByTitle(title); }
    @PostMapping public BookDto create(@RequestBody BookDto book) { return service.save(book); }
    @PutMapping("/{isbn}") public BookDto update(@PathVariable String isbn,@RequestBody BookDto book){ return service.save(new BookDto(isbn,book.title(),book.description(),book.edition(),book.categoryId(),book.publisherId())); }
    @DeleteMapping("/{isbn}") public void delete(@PathVariable String isbn) { service.delete(isbn); }
}
