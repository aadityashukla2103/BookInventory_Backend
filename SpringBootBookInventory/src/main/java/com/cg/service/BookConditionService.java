package com.cg.service;

import com.cg.dto.BookConditionDto;
import com.cg.entity.BookCondition;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookConditionService {
    private final BookConditionRepository repo;
    public BookConditionService(BookConditionRepository repo) { this.repo = repo; }
    public List<BookConditionDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public BookConditionDto get(Integer ranks){ return toDto(repo.findById(ranks).orElseThrow(() -> new ResourceNotFoundException("Condition not found"))); }
    public BookConditionDto save(BookConditionDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(Integer ranks){ repo.deleteById(ranks); }
    private BookConditionDto toDto(BookCondition c){ return new BookConditionDto(c.getRanks(), c.getDescription(), c.getFullDescription(), c.getPrice()); }
    private BookCondition toEntity(BookConditionDto d){ BookCondition c = new BookCondition(); c.setRanks(d.ranks()); c.setDescription(d.description()); c.setFullDescription(d.fullDescription()); c.setPrice(d.price()); return c; }
}
