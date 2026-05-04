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
        return categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryDto getById(Integer catId) {

        if (catId == null) {
            throw new IllegalArgumentException("Category ID must not be null");
        }

        Category entity = findEntity(catId);
        return toDto(entity);
    }

    public CategoryDto create(CategoryDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Category entity = new Category();
        entity.setCategoryName(dto.getCategoryName());

        return toDto(categoryRepository.save(entity));
    }

    public CategoryDto update(Integer catId, CategoryDto dto) {

        if (catId == null) {
            throw new IllegalArgumentException("Category ID must not be null");
        }

        if (dto == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Category entity = findEntity(catId);
        entity.setCategoryName(dto.getCategoryName());

        return toDto(categoryRepository.save(entity));
    }

    public void delete(Integer catId) {

        if (catId == null) {
            throw new IllegalArgumentException("Category ID must not be null");
        }

        Category entity = findEntity(catId);
        categoryRepository.delete(entity);
    }

    private Category findEntity(Integer catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + catId));
    }

    private CategoryDto toDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setCatId(entity.getCatId());
        dto.setCategoryName(entity.getCategoryName());
        return dto;
    }
}
