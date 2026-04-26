package com.cg.controller;

import com.cg.dto.PublisherDto;
import com.cg.service.PublisherService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {
    private final PublisherService service;
    public PublisherController(PublisherService service){ this.service=service; }
    @GetMapping public List<PublisherDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public PublisherDto one(@PathVariable Integer id){ return service.get(id); }
    @PostMapping public PublisherDto create(@RequestBody PublisherDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public PublisherDto update(@PathVariable Integer id,@RequestBody PublisherDto dto){ return service.save(new PublisherDto(id,dto.name(),dto.city(),dto.stateCode())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
