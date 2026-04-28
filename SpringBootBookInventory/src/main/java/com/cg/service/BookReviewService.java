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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewService {
    private final BookReviewRepository bookReviewRepository;
    private final BookRepository bookRepository;
    private final ReviewerRepository reviewerRepository;

    public BookReviewService(BookReviewRepository bookReviewRepository, BookRepository bookRepository, ReviewerRepository reviewerRepository) {
        this.bookReviewRepository = bookReviewRepository;
        this.bookRepository = bookRepository;
        this.reviewerRepository = reviewerRepository;
    }

    public List<BookReviewDto> getAll() { return bookReviewRepository.findAll().stream().map(this::toDto).toList(); }

    public BookReviewDto getById(String isbn, Integer reviewerId) {
        return toDto(findEntity(new BookReviewId(isbn, reviewerId)));
    }

    public BookReviewDto create(BookReviewDto dto) {
        BookReview entity = new BookReview();
        apply(dto, entity);
        return toDto(bookReviewRepository.save(entity));
    }

    public BookReviewDto update(String isbn, Integer reviewerId, BookReviewDto dto) {
        BookReview entity = findEntity(new BookReviewId(isbn, reviewerId));
        apply(dto, entity);
        return toDto(bookReviewRepository.save(entity));
    }

    public void delete(String isbn, Integer reviewerId) {
        bookReviewRepository.delete(findEntity(new BookReviewId(isbn, reviewerId)));
    }

    private BookReview findEntity(BookReviewId id) {
        return bookReviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookReview not found for isbn " + id.getIsbn() + " and reviewer " + id.getReviewerId()));
    }

    private void apply(BookReviewDto dto, BookReview entity) {
        Book book = bookRepository.findById(dto.getIsbn())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getIsbn()));
        Reviewer reviewer = reviewerRepository.findById(dto.getReviewerId())
                .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found with id: " + dto.getReviewerId()));
        entity.setId(new BookReviewId(dto.getIsbn(), dto.getReviewerId()));
        entity.setBook(book);
        entity.setReviewer(reviewer);
        entity.setRating(dto.getRating());
        entity.setComments(dto.getComments());
    }

    private BookReviewDto toDto(BookReview entity) {
        BookReviewDto dto = new BookReviewDto();
        dto.setIsbn(entity.getId() != null ? entity.getId().getIsbn() : null);
        dto.setReviewerId(entity.getId() != null ? entity.getId().getReviewerId() : null);
        dto.setRating(entity.getRating());
        dto.setComments(entity.getComments());
        return dto;
    }
}