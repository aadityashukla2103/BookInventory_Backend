package com.cg.service;

import com.cg.dto.BookReviewDto;
import com.cg.entity.*;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookRepository;
import com.cg.repo.BookReviewRepository;
import com.cg.repo.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookReviewService {
    private final BookReviewRepository repo;
    private final BookRepository bookRepository;
    private final ReviewerRepository reviewerRepository;

    public BookReviewService(BookReviewRepository repo, BookRepository bookRepository, ReviewerRepository reviewerRepository) {
        this.repo = repo;
        this.bookRepository = bookRepository;
        this.reviewerRepository = reviewerRepository;
    }

    public List<BookReviewDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public BookReviewDto save(BookReviewDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(String isbn, Integer reviewerId){ repo.deleteById(new BookReviewId(isbn, reviewerId)); }

    private BookReviewDto toDto(BookReview br){ return new BookReviewDto(br.getId().getIsbn(), br.getId().getReviewerId(), br.getRating(), br.getComments()); }
    private BookReview toEntity(BookReviewDto d){ BookReview br = new BookReview(); br.setId(new BookReviewId(d.isbn(), d.reviewerId())); br.setRating(d.rating()); br.setComments(d.comments()); br.setBook(bookRepository.findById(d.isbn()).orElseThrow(() -> new ResourceNotFoundException("Book not found"))); br.setReviewer(reviewerRepository.findById(d.reviewerId()).orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"))); return br; }
}
