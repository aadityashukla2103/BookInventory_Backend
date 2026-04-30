package com.cg.controller;
import jakarta.validation.Valid;

import com.cg.dto.BookConditionDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.BookConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-conditions")
public class BookConditionController {

    private final BookConditionService bookConditionService;

    public BookConditionController(BookConditionService bookConditionService) {
        this.bookConditionService = bookConditionService;
    }

    @GetMapping
    public List<BookConditionDto> getAll() {
        return bookConditionService.getAll();
    }

    @GetMapping("/{ranks}")
    public BookConditionDto getById(@PathVariable Integer ranks) {
        return bookConditionService.getById(ranks);
    }

    @PostMapping
    public ResponseEntity<BookConditionDto> create(@Valid @RequestBody BookConditionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookConditionService.create(dto));
    }

    @PutMapping("/{ranks}")
    public BookConditionDto update(@PathVariable Integer ranks,@Valid @RequestBody BookConditionDto dto) {
        return bookConditionService.update(ranks, dto);
    }

    @DeleteMapping("/{ranks}")
    public ResponseEntity<Void> delete(@PathVariable Integer ranks) {
        bookConditionService.delete(ranks);
        return ResponseEntity.noContent().build();
    }

}
