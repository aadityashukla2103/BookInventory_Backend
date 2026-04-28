package com.cg.service;

import com.cg.dto.CategoryDto;
import com.cg.entity.Category;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    public CategoryDto getById(Integer catId) {
        Category entity = findEntity(catId);
        return toDto(entity);
    }

    public CategoryDto create(CategoryDto dto) {
        Category entity = new Category();
        entity.setCatId(dto.getCatId());
        entity.setCategoryName(dto.getCategoryName());
        return toDto(categoryRepository.save(entity));
    }

    public CategoryDto update(Integer catId, CategoryDto dto) {
        Category entity = findEntity(catId);
        entity.setCategoryName(dto.getCategoryName());
        return toDto(categoryRepository.save(entity));
    }

    public void delete(Integer catId) {
        Category entity = findEntity(catId);
        categoryRepository.delete(entity);
    }

    private Category findEntity(Integer catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + catId));
    }

    private CategoryDto toDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setCatId(entity.getCatId());
        dto.setCategoryName(entity.getCategoryName());
        return dto;
    }
}