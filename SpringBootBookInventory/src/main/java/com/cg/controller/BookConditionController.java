package com.cg.controller;

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
    public ResponseEntity<BookConditionDto> create(@RequestBody BookConditionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookConditionService.create(dto));
    }

    @PutMapping("/{ranks}")
    public BookConditionDto update(@PathVariable Integer ranks, @RequestBody BookConditionDto dto) {
        return bookConditionService.update(ranks, dto);
    }

    @DeleteMapping("/{ranks}")
    public ResponseEntity<Void> delete(@PathVariable Integer ranks) {
        bookConditionService.delete(ranks);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long count() {
        return bookConditionService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return bookConditionService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<BookConditionDto> getFirst() {
        return bookConditionService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
    
    @GetMapping("/last")
    public ResponseEntity<BookConditionDto> getLast() {
        List<BookConditionDto> items = bookConditionService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    
    @GetMapping("/exists/{ranks}")
    public boolean exists(@PathVariable Integer ranks) {
        try {
            bookConditionService.getById(ranks);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}
