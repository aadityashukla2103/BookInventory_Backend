package com.cg.controller;

import com.cg.dto.ReviewerDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.ReviewerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewers")
public class ReviewerController {

    private final ReviewerService reviewerService;

    public ReviewerController(ReviewerService reviewerService) {
        this.reviewerService = reviewerService;
    }

    @GetMapping
    public List<ReviewerDto> getAll() {
        return reviewerService.getAll();
    }

    @GetMapping("/{reviewerId}")
    public ReviewerDto getById(@PathVariable Integer reviewerId) {
        return reviewerService.getById(reviewerId);
    }

    @PostMapping
    public ResponseEntity<ReviewerDto> create(@RequestBody ReviewerDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewerService.create(dto));
    }

    @PutMapping("/{reviewerId}")
    public ReviewerDto update(@PathVariable Integer reviewerId, @RequestBody ReviewerDto dto) {
        return reviewerService.update(reviewerId, dto);
    }

    @DeleteMapping("/{reviewerId}")
    public ResponseEntity<Void> delete(@PathVariable Integer reviewerId) {
        reviewerService.delete(reviewerId);
        return ResponseEntity.noContent().build();
    }
 }

