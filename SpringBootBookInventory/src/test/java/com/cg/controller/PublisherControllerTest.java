package com.cg.controller;

import com.cg.dto.PublisherDto;
import com.cg.service.PublisherService;
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

public class PublisherControllerTest {

    private MockMvc mockMvc;
    private PublisherService publisherService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {

        publisherService = Mockito.mock(PublisherService.class);
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders
                .standaloneSetup(new PublisherController(publisherService))
                .build();
    }

    @Test
    void testGetAll() throws Exception {

        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(1);
        dto.setName("ABC Publications");
        dto.setCity("Delhi");

        Mockito.when(publisherService.getAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].publisherId").value(1))
                .andExpect(jsonPath("$[0].name").value("ABC Publications"));
    }

    @Test
    void testGetById() throws Exception {

        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(1);
        dto.setName("ABC Publications");

        Mockito.when(publisherService.getById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/publishers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publisherId").value(1))
                .andExpect(jsonPath("$.name").value("ABC Publications"));
    }

    @Test
    void testCreate() throws Exception {

        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(1);
        dto.setName("ABC Publications");

        Mockito.when(publisherService.create(any(PublisherDto.class)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {

        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(1);
        dto.setName("Updated Publisher");

        Mockito.when(publisherService.update(eq(1), any(PublisherDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/publishers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {

        mockMvc.perform(delete("/api/publishers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCount() throws Exception {

        Mockito.when(publisherService.getAll())
                .thenReturn(List.of(new PublisherDto(), new PublisherDto()));

        mockMvc.perform(get("/api/publishers/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void testIsEmpty() throws Exception {

        Mockito.when(publisherService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/publishers/empty"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}