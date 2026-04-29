package com.cg.controller;

import com.cg.dto.BookDto;
import com.cg.service.BookService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    private MockMvc mockMvc;
    private BookService bookService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        bookService = Mockito.mock(BookService.class);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new BookController(bookService))
                .build();
    }

    @Test
    void testGetAll() throws Exception {

        BookDto dto = new BookDto();
        dto.setIsbn("101");

        Mockito.when(bookService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("101"));
    }

    @Test
    void testGetById() throws Exception {

        BookDto dto = new BookDto();
        dto.setIsbn("101");

        Mockito.when(bookService.getById("101")).thenReturn(dto);

        mockMvc.perform(get("/api/books/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("101"));
    }

    @Test
    void testCreate() throws Exception {

        BookDto dto = new BookDto();
        dto.setIsbn("101");

        Mockito.when(bookService.create(any(BookDto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {

        BookDto dto = new BookDto();
        dto.setIsbn("101");

        Mockito.when(bookService.update(eq("101"), any(BookDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/books/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(delete("/api/books/101"))
                .andExpect(status().isNoContent());
    }
}