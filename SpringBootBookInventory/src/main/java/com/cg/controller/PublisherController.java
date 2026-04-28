package com.cg.controller;

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
    public List<PublisherDto> getAll() { return publisherService.getAll(); }

    @GetMapping("/{publisherId}")
    public PublisherDto getById(@PathVariable Integer publisherId) { return publisherService.getById(publisherId); }

    @PostMapping
    public ResponseEntity<PublisherDto> create(@RequestBody PublisherDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.create(dto));
    }

    @PutMapping("/{publisherId}")
    public PublisherDto update(@PathVariable Integer publisherId, @RequestBody PublisherDto dto) {
        return publisherService.update(publisherId, dto);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> delete(@PathVariable Integer publisherId) {
        publisherService.delete(publisherId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    public long count() {
        return publisherService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return publisherService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<PublisherDto> getFirst() {
        return publisherService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    

    @GetMapping("/last")
    public ResponseEntity<PublisherDto> getLast() {
        List<PublisherDto> items = publisherService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    
    @GetMapping("/exists/{publisherId}")
    public boolean exists(@PathVariable Integer publisherId) {
        try {
            publisherService.getById(publisherId);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}