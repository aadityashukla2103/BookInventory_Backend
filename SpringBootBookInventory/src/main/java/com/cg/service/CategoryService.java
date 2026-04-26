package com.cg.service;

import com.cg.dto.CategoryDto;
import com.cg.entity.Category;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repo;
    public CategoryService(CategoryRepository repo) { this.repo = repo; }
    public List<CategoryDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public CategoryDto get(Integer id){ return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"))); }
    public CategoryDto save(CategoryDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(Integer id){ repo.deleteById(id); }
    private CategoryDto toDto(Category c){ return new CategoryDto(c.getCatId(), c.getCategoryName()); }
    private Category toEntity(CategoryDto d){ Category c=new Category(); c.setCatId(d.catId()); c.setCategoryName(d.categoryName()); return c; }
}
