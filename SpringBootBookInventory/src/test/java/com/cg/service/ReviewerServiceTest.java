package com.cg.service;

import com.cg.dto.ReviewerDto;
import com.cg.entity.Reviewer;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.ReviewerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewerServiceTest {

    @Mock
    private ReviewerRepository reviewerRepository;

    @InjectMocks
    private ReviewerService reviewerService;

    @Test
    void getAllShouldReturnList() {

        Reviewer r1 = new Reviewer();
        r1.setReviewerId(1);
        r1.setName("Amit");
        r1.setEmployedBy("TCS");

        Reviewer r2 = new Reviewer();
        r2.setReviewerId(2);
        r2.setName("Rahul");
        r2.setEmployedBy("Infosys");

        when(reviewerRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<ReviewerDto> result = reviewerService.getAll();

        assertEquals(2, result.size());
        assertEquals("Amit", result.get(0).getName());
        assertEquals("Rahul", result.get(1).getName());
        assertEquals("TCS", result.get(0).getEmployedBy());

        verify(reviewerRepository).findAll();
    }

    @Test
    void getByIdShouldReturnReviewer() {

        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(1);
        reviewer.setName("Amit");
        reviewer.setEmployedBy("TCS");

        when(reviewerRepository.findById(1)).thenReturn(Optional.of(reviewer));

        ReviewerDto result = reviewerService.getById(1);

        assertEquals(1, result.getReviewerId());
        assertEquals("Amit", result.getName());
        assertEquals("TCS", result.getEmployedBy());

        verify(reviewerRepository).findById(1);
    }

    @Test
    void getByIdShouldThrowWhenNotFound() {

        when(reviewerRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> reviewerService.getById(99));

        assertEquals("Reviewer not found with id: 99", ex.getMessage());
    }

    @Test
    void createShouldReturnDto() {

        ReviewerDto request = new ReviewerDto();
        request.setReviewerId(1);
        request.setName("Amit");
        request.setEmployedBy("TCS");

        when(reviewerRepository.save(any(Reviewer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReviewerDto result = reviewerService.create(request);

        assertEquals(1, result.getReviewerId());
        assertEquals("Amit", result.getName());
        assertEquals("TCS", result.getEmployedBy());

        verify(reviewerRepository).save(any(Reviewer.class));
    }

    @Test
    void updateShouldReturnDto() {

        Reviewer existing = new Reviewer();
        existing.setReviewerId(1);
        existing.setName("Old");
        existing.setEmployedBy("Old Company");

        ReviewerDto request = new ReviewerDto();
        request.setName("Updated");
        request.setEmployedBy("Wipro");

        when(reviewerRepository.findById(1)).thenReturn(Optional.of(existing));
        when(reviewerRepository.save(any(Reviewer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ReviewerDto result = reviewerService.update(1, request);

        assertEquals(1, result.getReviewerId());
        assertEquals("Updated", result.getName());
        assertEquals("Wipro", result.getEmployedBy());

        verify(reviewerRepository).findById(1);
        verify(reviewerRepository).save(existing);
    }

    @Test
    void updateShouldThrowWhenNotFound() {

        ReviewerDto request = new ReviewerDto();
        request.setName("Test");

        when(reviewerRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> reviewerService.update(99, request));

        assertEquals("Reviewer not found with id: 99", ex.getMessage());

        verify(reviewerRepository, never()).save(any());
    }

    @Test
    void deleteShouldRemoveEntity() {

        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(1);
        reviewer.setName("Amit");

        when(reviewerRepository.findById(1)).thenReturn(Optional.of(reviewer));

        reviewerService.delete(1);

        verify(reviewerRepository).findById(1);
        verify(reviewerRepository).delete(reviewer);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {

        when(reviewerRepository.findById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> reviewerService.delete(99));

        assertEquals("Reviewer not found with id: 99", ex.getMessage());

        verify(reviewerRepository, never()).delete(any());
    }
}