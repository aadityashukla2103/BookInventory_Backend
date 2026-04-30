package com.cg.controller;

import com.cg.dto.ReviewerDto;
import com.cg.service.ReviewerService;
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
class ReviewerControllerTest {

    @Mock
    private ReviewerService reviewerService;

    @InjectMocks
    private ReviewerController reviewerController;

    @Test
    void getAllShouldReturnList() {

        ReviewerDto r1 = new ReviewerDto();
        r1.setReviewerId(1);
        r1.setName("Amit");

        ReviewerDto r2 = new ReviewerDto();
        r2.setReviewerId(2);
        r2.setName("Rahul");

        when(reviewerService.getAll()).thenReturn(Arrays.asList(r1, r2));

        List<ReviewerDto> result = reviewerController.getAll();

        assertEquals(2, result.size());
        assertEquals("Amit", result.get(0).getName());
        assertEquals("Rahul", result.get(1).getName());

        verify(reviewerService).getAll();
    }

    @Test
    void getByIdShouldReturnReviewer() {

        ReviewerDto dto = new ReviewerDto();
        dto.setReviewerId(1);
        dto.setName("Amit");

        when(reviewerService.getById(1)).thenReturn(dto);

        ReviewerDto result = reviewerController.getById(1);

        assertEquals(1, result.getReviewerId());
        assertEquals("Amit", result.getName());

        verify(reviewerService).getById(1);
    }

    @Test
    void createShouldReturnCreatedResponse() {

        ReviewerDto request = new ReviewerDto();
        request.setReviewerId(1);
        request.setName("Amit");

        ReviewerDto response = new ReviewerDto();
        response.setReviewerId(1);
        response.setName("Amit");

        when(reviewerService.create(request)).thenReturn(response);

        ResponseEntity<ReviewerDto> result =
                reviewerController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getReviewerId());
        assertEquals("Amit", result.getBody().getName());

        verify(reviewerService).create(request);
    }

    @Test
    void updateShouldReturnUpdatedReviewer() {

        ReviewerDto request = new ReviewerDto();
        request.setName("Updated Name");

        ReviewerDto response = new ReviewerDto();
        response.setReviewerId(1);
        response.setName("Updated Name");

        when(reviewerService.update(1, request)).thenReturn(response);

        ReviewerDto result = reviewerController.update(1, request);

        assertEquals(1, result.getReviewerId());
        assertEquals("Updated Name", result.getName());

        verify(reviewerService).update(1, request);
    }

    @Test
    void deleteShouldReturnNoContent() {

        doNothing().when(reviewerService).delete(1);

        ResponseEntity<Void> result = reviewerController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(reviewerService).delete(1);
    }
}