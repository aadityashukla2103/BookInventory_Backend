package com.cg.controller;

import com.cg.dto.PermRoleDto;
import com.cg.service.PermRoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermRoleControllerTest {

    @Mock
    private PermRoleService permRoleService;

    @InjectMocks
    private PermRoleController permRoleController;

    private PermRoleDto dto;

    @BeforeEach
    void setUp() {
        dto = new PermRoleDto();
        dto.setRoleNumber(1);
        dto.setPermRole("ADMIN");
    }

    @Test
    void testGetAll() {
        when(permRoleService.getAll()).thenReturn(List.of(dto));

        List<PermRoleDto> result = permRoleController.getAll();

        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getPermRole());
        verify(permRoleService, times(1)).getAll();
    }

    @Test
    void testGetById() {
        when(permRoleService.getById(1)).thenReturn(dto);

        PermRoleDto result = permRoleController.getById(1);

        assertEquals(1, result.getRoleNumber());
        assertEquals("ADMIN", result.getPermRole());
    }

    @Test
    void testCreate() {
        when(permRoleService.create(dto)).thenReturn(dto);

        ResponseEntity<PermRoleDto> response = permRoleController.create(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        when(permRoleService.update(1, dto)).thenReturn(dto);

        PermRoleDto result = permRoleController.update(1, dto);

        assertEquals("ADMIN", result.getPermRole());
        verify(permRoleService, times(1)).update(1, dto);
    }

    @Test
    void testDelete() {
        ResponseEntity<Void> response = permRoleController.delete(1);

        verify(permRoleService, times(1)).delete(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}
