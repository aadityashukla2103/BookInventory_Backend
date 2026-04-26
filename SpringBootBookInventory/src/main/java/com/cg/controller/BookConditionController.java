package com.cg.controller;

import com.cg.dto.BookConditionDto;
import com.cg.service.BookConditionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/book-conditions")
public class BookConditionController {
    private final BookConditionService service;
    public BookConditionController(BookConditionService service){ this.service=service; }
    @GetMapping public List<BookConditionDto> all(){ return service.getAll(); }
    @GetMapping("/{ranks}") public BookConditionDto one(@PathVariable Integer ranks){ return service.get(ranks); }
    @PostMapping public BookConditionDto create(@RequestBody BookConditionDto dto){ return service.save(dto); }
    @PutMapping("/{ranks}") public BookConditionDto update(@PathVariable Integer ranks,@RequestBody BookConditionDto dto){ return service.save(new BookConditionDto(ranks,dto.description(),dto.fullDescription(),dto.price())); }
    @DeleteMapping("/{ranks}") public void delete(@PathVariable Integer ranks){ service.delete(ranks); }
}
