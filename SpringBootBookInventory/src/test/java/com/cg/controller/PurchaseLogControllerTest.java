package com.cg.controller;

import com.cg.dto.PurchaseLogDto;
import com.cg.service.PurchaseLogService;
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
class PurchaseLogControllerTest {

    @Mock
    private PurchaseLogService purchaseLogService;

    @InjectMocks
    private PurchaseLogController purchaseLogController;

    private PurchaseLogDto createDto() {
        PurchaseLogDto dto = new PurchaseLogDto();
        dto.setUserID(1);
        dto.setInventoryID(100);
        return dto;
    }

    @Test
    void getAllShouldReturnList() {

        PurchaseLogDto p1 = createDto();
        PurchaseLogDto p2 = createDto();
        p2.setInventoryID(200);

        when(purchaseLogService.getAll()).thenReturn(Arrays.asList(p1, p2));

        List<PurchaseLogDto> result = purchaseLogController.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getUserID());
        assertEquals(100, result.get(0).getInventoryID());

        verify(purchaseLogService).getAll();
    }

    @Test
    void getByIdShouldReturnPurchaseLog() {

        PurchaseLogDto dto = createDto();

        when(purchaseLogService.getById(1, 100)).thenReturn(dto);

        PurchaseLogDto result = purchaseLogController.getById(1, 100);

        assertEquals(1, result.getUserID());
        assertEquals(100, result.getInventoryID());

        verify(purchaseLogService).getById(1, 100);
    }

    @Test
    void createShouldReturnCreatedResponse() {

        PurchaseLogDto request = createDto();
        PurchaseLogDto response = createDto();

        when(purchaseLogService.create(request)).thenReturn(response);

        ResponseEntity<PurchaseLogDto> result =
                purchaseLogController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getUserID());

        verify(purchaseLogService).create(request);
    }

    @Test
    void updateShouldReturnUpdatedPurchaseLog() {

        PurchaseLogDto request = createDto();
        PurchaseLogDto response = createDto();

        when(purchaseLogService.update(1, 100, request)).thenReturn(response);

        PurchaseLogDto result =
                purchaseLogController.update(1, 100, request);

        assertEquals(1, result.getUserID());
        assertEquals(100, result.getInventoryID());

        verify(purchaseLogService).update(1, 100, request);
    }

    @Test
    void deleteShouldReturnNoContent() {

        doNothing().when(purchaseLogService).delete(1, 100);

        ResponseEntity<Void> result =
                purchaseLogController.delete(1, 100);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(purchaseLogService).delete(1, 100);
    }
}