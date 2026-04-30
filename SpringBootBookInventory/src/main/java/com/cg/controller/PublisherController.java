package com.cg.controller;
import jakarta.validation.Valid;

import com.cg.dto.PublisherDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) { this.publisherService = publisherService; }

    @GetMapping
    public List<PublisherDto> getAll() { 
    	return publisherService.getAll();
    }

    @GetMapping("/{publisherId}")
    public PublisherDto getById(@PathVariable Integer publisherId) { return publisherService.getById(publisherId); }

    @PostMapping
    public ResponseEntity<PublisherDto> create(@Valid @RequestBody PublisherDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.create(dto));
    }

    @PutMapping("/{publisherId}")
    public PublisherDto update(@PathVariable Integer publisherId,@Valid @RequestBody PublisherDto dto) {
        return publisherService.update(publisherId, dto);
    }

    @GetMapping("/count")
    public long count() {
        return publisherService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return publisherService.getAll().isEmpty();
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> delete(@PathVariable Integer publisherId) {
        publisherService.delete(publisherId);
        return ResponseEntity.noContent().build();
    }

}