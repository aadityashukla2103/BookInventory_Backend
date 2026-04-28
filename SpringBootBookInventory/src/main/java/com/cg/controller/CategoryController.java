package com.cg.controller;

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
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(dto));
    }

    @PutMapping("/{catId}")
    public CategoryDto update(@PathVariable Integer catId, @RequestBody CategoryDto dto) {
        return categoryService.update(catId, dto);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> delete(@PathVariable Integer catId) {
        categoryService.delete(catId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    public long count() {
        return categoryService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return categoryService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<CategoryDto> getFirst() {
        return categoryService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    

    @GetMapping("/last")
    public ResponseEntity<CategoryDto> getLast() {
        List<CategoryDto> items = categoryService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }

    @GetMapping("/exists/{catId}")
    public boolean exists(@PathVariable Integer catId) {
        try {
            categoryService.getById(catId);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}