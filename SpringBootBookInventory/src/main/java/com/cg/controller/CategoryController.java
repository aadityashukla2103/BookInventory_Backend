package com.cg.controller;

import com.cg.dto.CategoryDto;
import com.cg.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;
    public CategoryController(CategoryService service){ this.service=service; }
    @GetMapping public List<CategoryDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public CategoryDto one(@PathVariable Integer id){ return service.get(id); }
    @PostMapping public CategoryDto create(@RequestBody CategoryDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public CategoryDto update(@PathVariable Integer id,@RequestBody CategoryDto dto){ return service.save(new CategoryDto(id,dto.categoryName())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
