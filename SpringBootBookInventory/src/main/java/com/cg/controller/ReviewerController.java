package com.cg.controller;

import com.cg.dto.ReviewerDto;
import com.cg.service.ReviewerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {
    private final ReviewerService service;
    public ReviewerController(ReviewerService service){ this.service=service; }
    @GetMapping public List<ReviewerDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public ReviewerDto one(@PathVariable Integer id){ return service.get(id); }
    @PostMapping public ReviewerDto create(@RequestBody ReviewerDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public ReviewerDto update(@PathVariable Integer id,@RequestBody ReviewerDto dto){ return service.save(new ReviewerDto(id,dto.name(),dto.employedBy())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
