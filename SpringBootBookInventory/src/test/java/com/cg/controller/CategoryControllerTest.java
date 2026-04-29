package com.cg.controller;

import com.cg.dto.CategoryDto;
import com.cg.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private MockMvc mockMvc;
    private CategoryService categoryService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        categoryService = Mockito.mock(CategoryService.class);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new CategoryController(categoryService))
                .build();
    }

    @Test
    void testGetAll() throws Exception {

        CategoryDto dto = new CategoryDto();
        dto.setCatId(1);
        dto.setCategoryName("Technology");

        Mockito.when(categoryService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].catId").value(1))
                .andExpect(jsonPath("$[0].categoryName").value("Technology"));
    }

    @Test
    void testGetById() throws Exception {

        CategoryDto dto = new CategoryDto();
        dto.setCatId(1);
        dto.setCategoryName("Technology");

        Mockito.when(categoryService.getById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.catId").value(1))
                .andExpect(jsonPath("$.categoryName").value("Technology"));
    }

    @Test
    void testCreate() throws Exception {

        CategoryDto dto = new CategoryDto();
        dto.setCatId(1);
        dto.setCategoryName("Technology");

        Mockito.when(categoryService.create(any(CategoryDto.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {

        CategoryDto dto = new CategoryDto();
        dto.setCatId(1);
        dto.setCategoryName("Updated Category");

        Mockito.when(categoryService.update(eq(1), any(CategoryDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCount() throws Exception {

        Mockito.when(categoryService.getAll())
                .thenReturn(List.of(new CategoryDto(), new CategoryDto()));

        mockMvc.perform(get("/api/categories/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void testIsEmpty() throws Exception {

        Mockito.when(categoryService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/categories/empty"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}