package com.cg.service;

import com.cg.dto.CategoryDto;
import com.cg.entity.Category;
import com.cg.repo.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    @Test
    void testGetById() {
        CategoryRepository repo = Mockito.mock(CategoryRepository.class);

        Category c = new Category();
        c.setCatId(1);
        c.setCategoryName("Tech");

        Mockito.when(repo.findById(1)).thenReturn(Optional.of(c));

        CategoryService service = new CategoryService(repo);

        CategoryDto dto = service.getById(1);

        assertEquals("Tech", dto.getCategoryName());
    }
}