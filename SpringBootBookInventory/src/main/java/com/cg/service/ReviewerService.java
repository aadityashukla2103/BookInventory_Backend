package com.cg.service;

import com.cg.dto.ReviewerDto;
import com.cg.entity.Reviewer;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerService {

    private final ReviewerRepository reviewerRepository;

    public ReviewerService(ReviewerRepository reviewerRepository) {
        this.reviewerRepository = reviewerRepository;
    }

    public List<ReviewerDto> getAll() {
        return reviewerRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ReviewerDto getById(Integer reviewerId) {
        if (reviewerId == null) {
            throw new IllegalArgumentException("Reviewer ID cannot be null");
        }

        Reviewer entity = findEntity(reviewerId);
        return toDto(entity);
    }

    public ReviewerDto create(ReviewerDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Reviewer data cannot be null");
        }

        Reviewer entity = new Reviewer();
        entity.setReviewerId(dto.getReviewerId());
        entity.setName(dto.getName());
        entity.setEmployedBy(dto.getEmployedBy());

        return toDto(reviewerRepository.save(entity));
    }

    public ReviewerDto update(Integer reviewerId, ReviewerDto dto) {
        if (reviewerId == null || dto == null) {
            throw new IllegalArgumentException("Invalid input for update");
        }

        Reviewer entity = findEntity(reviewerId);
        entity.setName(dto.getName());
        entity.setEmployedBy(dto.getEmployedBy());

        return toDto(reviewerRepository.save(entity));
    }

    public void delete(Integer reviewerId) {
        if (reviewerId == null) {
            throw new IllegalArgumentException("Reviewer ID cannot be null");
        }

        Reviewer entity = findEntity(reviewerId);
        reviewerRepository.delete(entity);
    }

    private Reviewer findEntity(Integer reviewerId) {
        return reviewerRepository.findById(reviewerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Reviewer not found with id: " + reviewerId));
    }

    private ReviewerDto toDto(Reviewer entity) {
        ReviewerDto dto = new ReviewerDto();
        dto.setReviewerId(entity.getReviewerId());
        dto.setName(entity.getName());
        dto.setEmployedBy(entity.getEmployedBy());
        return dto;
    }
}