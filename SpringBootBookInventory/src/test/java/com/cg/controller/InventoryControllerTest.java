package com.cg.controller;

import com.cg.dto.InventoryDto;
import com.cg.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @Test
    void getAllShouldReturnList() {

        InventoryDto i1 = new InventoryDto();
        i1.setInventoryID(1);

        InventoryDto i2 = new InventoryDto();
        i2.setInventoryID(2);

        when(inventoryService.getAll()).thenReturn(Arrays.asList(i1, i2));

        List<InventoryDto> result = inventoryController.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getInventoryID());
        assertEquals(2, result.get(1).getInventoryID());

        verify(inventoryService).getAll();
    }

    @Test
    void getByIdShouldReturnInventory() {

        InventoryDto dto = new InventoryDto();
        dto.setInventoryID(1);

        when(inventoryService.getById(1)).thenReturn(dto);

        InventoryDto result = inventoryController.getById(1);

        assertEquals(1, result.getInventoryID());

        verify(inventoryService).getById(1);
    }

    @Test
    void createShouldReturnCreatedResponse() {

        InventoryDto request = new InventoryDto();
        request.setInventoryID(1);

        InventoryDto response = new InventoryDto();
        response.setInventoryID(1);

        when(inventoryService.create(request)).thenReturn(response);

        ResponseEntity<InventoryDto> result =
                inventoryController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getInventoryID());

        verify(inventoryService).create(request);
    }

    @Test
    void updateShouldReturnUpdatedInventory() {

        InventoryDto request = new InventoryDto();

        InventoryDto response = new InventoryDto();
        response.setInventoryID(1);

        when(inventoryService.update(1, request)).thenReturn(response);

        InventoryDto result = inventoryController.update(1, request);

        assertEquals(1, result.getInventoryID());

        verify(inventoryService).update(1, request);
    }

    @Test
    void deleteShouldReturnNoContent() {

        doNothing().when(inventoryService).delete(1);

        ResponseEntity<Void> result = inventoryController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(inventoryService).delete(1);
    }
}