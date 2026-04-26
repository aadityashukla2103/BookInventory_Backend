package com.cg.controller;

import com.cg.dto.AuthorDto;
import com.cg.service.AuthorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService service;
    public AuthorController(AuthorService service){ this.service = service; }
    @GetMapping public List<AuthorDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public AuthorDto one(@PathVariable Integer id){ return service.get(id); }
    @PostMapping public AuthorDto create(@RequestBody AuthorDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public AuthorDto update(@PathVariable Integer id,@RequestBody AuthorDto dto){ return service.save(new AuthorDto(id,dto.firstName(),dto.lastName(),dto.photo())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
