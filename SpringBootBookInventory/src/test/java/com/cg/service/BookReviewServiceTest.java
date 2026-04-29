package com.cg.service;

import com.cg.dto.BookReviewDto;
import com.cg.entity.Book;
import com.cg.entity.BookReview;
import com.cg.entity.BookReviewId;
import com.cg.entity.Reviewer;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookRepository;
import com.cg.repo.BookReviewRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookReviewServiceTest {

    @Mock
    private BookReviewRepository bookReviewRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ReviewerRepository reviewerRepository;

    @InjectMocks
    private BookReviewService bookReviewService;

    @Test
    void getAllShouldReturnList() {

        BookReview r1 = new BookReview();
        r1.setId(new BookReviewId("111", 1));
        r1.setRating(5);
        r1.setComments("Excellent");

        BookReview r2 = new BookReview();
        r2.setId(new BookReviewId("222", 2));
        r2.setRating(4);
        r2.setComments("Good");

        when(bookReviewRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<BookReviewDto> result = bookReviewService.getAll();

        assertEquals(2, result.size());
        assertEquals("111", result.get(0).getIsbn());
        assertEquals(1, result.get(0).getReviewerId());
        assertEquals(5, result.get(0).getRating());

        verify(bookReviewRepository).findAll();
    }

    @Test
    void getByIdShouldReturnRecord() {

        BookReview entity = new BookReview();
        entity.setId(new BookReviewId("123", 7));
        entity.setRating(5);
        entity.setComments("Great");

        when(bookReviewRepository.findById(any(BookReviewId.class)))
                .thenReturn(Optional.of(entity));

        BookReviewDto result = bookReviewService.getById("123", 7);

        assertEquals("123", result.getIsbn());
        assertEquals(7, result.getReviewerId());
        assertEquals(5, result.getRating());
        assertEquals("Great", result.getComments());
    }

    @Test
    void getByIdShouldThrowWhenNotFound() {

        when(bookReviewRepository.findById(any(BookReviewId.class)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookReviewService.getById("123", 1));

        assertEquals(
                "BookReview not found for isbn 123 and reviewer 1",
                ex.getMessage()
        );
    }

    @Test
    void createShouldReturnDto() {

        Book book = new Book();
        Reviewer reviewer = new Reviewer();

        BookReviewDto request = new BookReviewDto();
        request.setIsbn("123");
        request.setReviewerId(1);
        request.setRating(5);
        request.setComments("Excellent");

        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(reviewerRepository.findById(1)).thenReturn(Optional.of(reviewer));
        when(bookReviewRepository.save(any(BookReview.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookReviewDto result = bookReviewService.create(request);

        assertEquals("123", result.getIsbn());
        assertEquals(1, result.getReviewerId());
        assertEquals(5, result.getRating());
        assertEquals("Excellent", result.getComments());
    }

    @Test
    void createShouldThrowWhenBookNotFound() {

        BookReviewDto request = new BookReviewDto();
        request.setIsbn("123");
        request.setReviewerId(1);

        when(bookRepository.findById("123")).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookReviewService.create(request));

        assertEquals("Book not found with id: 123", ex.getMessage());
    }

    @Test
    void createShouldThrowWhenReviewerNotFound() {

        Book book = new Book();

        BookReviewDto request = new BookReviewDto();
        request.setIsbn("123");
        request.setReviewerId(1);

        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(reviewerRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookReviewService.create(request));

        assertEquals("Reviewer not found with id: 1", ex.getMessage());
    }

    @Test
    void updateShouldReturnDto() {

        BookReview existing = new BookReview();
        existing.setId(new BookReviewId("123", 1));

        Book book = new Book();
        Reviewer reviewer = new Reviewer();

        BookReviewDto request = new BookReviewDto();
        request.setIsbn("123");
        request.setReviewerId(1);
        request.setRating(4);
        request.setComments("Good");

        when(bookReviewRepository.findById(any(BookReviewId.class)))
                .thenReturn(Optional.of(existing));
        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(reviewerRepository.findById(1)).thenReturn(Optional.of(reviewer));
        when(bookReviewRepository.save(any(BookReview.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookReviewDto result = bookReviewService.update("123", 1, request);

        assertEquals("123", result.getIsbn());
        assertEquals(1, result.getReviewerId());
        assertEquals(4, result.getRating());
        assertEquals("Good", result.getComments());
    }

    @Test
    void updateShouldThrowWhenNotFound() {

        BookReviewDto request = new BookReviewDto();

        when(bookReviewRepository.findById(any(BookReviewId.class)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookReviewService.update("123", 1, request));

        assertEquals(
                "BookReview not found for isbn 123 and reviewer 1",
                ex.getMessage()
        );
    }

    @Test
    void deleteShouldRemoveEntity() {

        BookReview entity = new BookReview();
        entity.setId(new BookReviewId("123", 1));

        when(bookReviewRepository.findById(any(BookReviewId.class)))
                .thenReturn(Optional.of(entity));

        bookReviewService.delete("123", 1);

        verify(bookReviewRepository).delete(entity);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {

        when(bookReviewRepository.findById(any(BookReviewId.class)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookReviewService.delete("123", 1));

        assertEquals(
                "BookReview not found for isbn 123 and reviewer 1",
                ex.getMessage()
        );
    }
}