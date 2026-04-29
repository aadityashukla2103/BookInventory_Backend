package com.cg.controller;

import com.cg.dto.BookConditionDto;
import com.cg.service.BookConditionService;
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
class BookConditionControllerTest {

    @Mock
    private BookConditionService bookConditionService;

    @InjectMocks
    private BookConditionController bookConditionController;

    private BookConditionDto createDto() {
        BookConditionDto dto = new BookConditionDto();
        dto.setRanks(1);
        dto.setDescription("New");
        dto.setFullDescription("Brand new book");
        dto.setPrice(500.0);
        return dto;
    }

    @Test
    void getAllShouldReturnList() {

        BookConditionDto c1 = createDto();
        BookConditionDto c2 = createDto();
        c2.setRanks(2);

        when(bookConditionService.getAll()).thenReturn(Arrays.asList(c1, c2));

        List<BookConditionDto> result = bookConditionController.getAll();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getRanks());
        assertEquals(2, result.get(1).getRanks());

        verify(bookConditionService).getAll();
    }

    @Test
    void getByIdShouldReturnBookCondition() {

        BookConditionDto dto = createDto();

        when(bookConditionService.getById(1)).thenReturn(dto);

        BookConditionDto result = bookConditionController.getById(1);

        assertEquals(1, result.getRanks());

        verify(bookConditionService).getById(1);
    }

    @Test
    void createShouldReturnCreatedResponse() {

        BookConditionDto request = createDto();
        BookConditionDto response = createDto();

        when(bookConditionService.create(request)).thenReturn(response);

        ResponseEntity<BookConditionDto> result =
                bookConditionController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getRanks());

        verify(bookConditionService).create(request);
    }

    @Test
    void updateShouldReturnUpdatedBookCondition() {

        BookConditionDto request = createDto();
        BookConditionDto response = createDto();

        when(bookConditionService.update(1, request)).thenReturn(response);

        BookConditionDto result =
                bookConditionController.update(1, request);

        assertEquals(1, result.getRanks());

        verify(bookConditionService).update(1, request);
    }

    @Test
    void deleteShouldReturnNoContent() {

        doNothing().when(bookConditionService).delete(1);

        ResponseEntity<Void> result =
                bookConditionController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(bookConditionService).delete(1);
    }
    
}
