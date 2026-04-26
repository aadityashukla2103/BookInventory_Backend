package com.cg.service;

import com.cg.dto.ReviewerDto;
import com.cg.entity.Reviewer;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewerService {
    private final ReviewerRepository repo;
    public ReviewerService(ReviewerRepository repo) { this.repo = repo; }
    public List<ReviewerDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public ReviewerDto get(Integer id){ return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"))); }
    public ReviewerDto save(ReviewerDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(Integer id){ repo.deleteById(id); }
    private ReviewerDto toDto(Reviewer r){ return new ReviewerDto(r.getReviewerId(), r.getName(), r.getEmployedBy()); }
    private Reviewer toEntity(ReviewerDto d){ Reviewer r = new Reviewer(); r.setReviewerId(d.reviewerId()); r.setName(d.name()); r.setEmployedBy(d.employedBy()); return r; }
}
