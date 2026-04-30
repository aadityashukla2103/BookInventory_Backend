package com.cg.controller;
import jakarta.validation.Valid;

import com.cg.dto.CategoryDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Integer catId) {
        return categoryService.getById(catId);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(dto));
    }

    @PutMapping("/{catId}")
    public CategoryDto update(@PathVariable Integer catId,@Valid @RequestBody CategoryDto dto) {
        return categoryService.update(catId, dto);
    }


    @GetMapping("/count")
    public long count() {
        return categoryService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return categoryService.getAll().isEmpty();
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> delete(@PathVariable Integer catId) {
        categoryService.delete(catId);
        return ResponseEntity.noContent().build();
    }
}