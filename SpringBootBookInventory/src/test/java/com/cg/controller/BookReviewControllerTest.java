package com.cg.controller;

import com.cg.dto.BookReviewDto;
import com.cg.service.BookReviewService;
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
class BookReviewControllerTest {

    @Mock
    private BookReviewService bookReviewService;

    @InjectMocks
    private BookReviewController bookReviewController;

    @Test
    void getAllShouldReturnList() {

        BookReviewDto d1 = new BookReviewDto();
        d1.setIsbn("111");
        d1.setReviewerId(1);

        BookReviewDto d2 = new BookReviewDto();
        d2.setIsbn("222");
        d2.setReviewerId(2);

        when(bookReviewService.getAll()).thenReturn(Arrays.asList(d1, d2));

        List<BookReviewDto> result = bookReviewController.getAll();

        assertEquals(2, result.size());
        assertEquals("111", result.get(0).getIsbn());
        assertEquals(1, result.get(0).getReviewerId());
        assertEquals("222", result.get(1).getIsbn());

        verify(bookReviewService).getAll();
    }

    @Test
    void getByIdShouldReturnRecord() {

        BookReviewDto dto = new BookReviewDto();
        dto.setIsbn("123");
        dto.setReviewerId(7);

        when(bookReviewService.getById("123", 7)).thenReturn(dto);

        BookReviewDto result = bookReviewController.getById("123", 7);

        assertEquals("123", result.getIsbn());
        assertEquals(7, result.getReviewerId());

        verify(bookReviewService).getById("123", 7);
    }

    @Test
    void createShouldReturnCreatedResponse() {

        BookReviewDto request = new BookReviewDto();
        request.setIsbn("123");
        request.setReviewerId(1);

        BookReviewDto response = new BookReviewDto();
        response.setIsbn("123");
        response.setReviewerId(1);

        when(bookReviewService.create(request)).thenReturn(response);

        ResponseEntity<BookReviewDto> result =
                bookReviewController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("123", result.getBody().getIsbn());
        assertEquals(1, result.getBody().getReviewerId());

        verify(bookReviewService).create(request);
    }

    @Test
    void updateShouldReturnUpdatedRecord() {

        BookReviewDto request = new BookReviewDto();

        BookReviewDto response = new BookReviewDto();
        response.setIsbn("123");
        response.setReviewerId(1);

        when(bookReviewService.update("123", 1, request))
                .thenReturn(response);

        BookReviewDto result =
                bookReviewController.update("123", 1, request);

        assertEquals("123", result.getIsbn());
        assertEquals(1, result.getReviewerId());

        verify(bookReviewService).update("123", 1, request);
    }

    @Test
    void deleteShouldReturnNoContent() {

        doNothing().when(bookReviewService).delete("123", 1);

        ResponseEntity<Void> result =
                bookReviewController.delete("123", 1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(bookReviewService).delete("123", 1);
    }
}