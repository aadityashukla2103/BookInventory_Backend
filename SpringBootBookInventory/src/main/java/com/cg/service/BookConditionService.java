package com.cg.service;

import com.cg.dto.BookConditionDto;
import com.cg.entity.BookCondition;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookConditionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookConditionService {

    private final BookConditionRepository bookConditionRepository;

    public BookConditionService(BookConditionRepository bookConditionRepository) {
        this.bookConditionRepository = bookConditionRepository;
    }

    public List<BookConditionDto> getAll() {
        return bookConditionRepository.findAll().stream().map(this::toDto).toList();
    }

    public BookConditionDto getById(Integer ranks) {
        BookCondition entity = findEntity(ranks);
        return toDto(entity);
    }

    public BookConditionDto create(BookConditionDto dto) {
        BookCondition entity = new BookCondition();
        entity.setRanks(dto.getRanks());
        entity.setDescription(dto.getDescription());
        entity.setFullDescription(dto.getFullDescription());
        entity.setPrice(dto.getPrice());
        return toDto(bookConditionRepository.save(entity));
    }

    public BookConditionDto update(Integer ranks, BookConditionDto dto) {
        BookCondition entity = findEntity(ranks);
        entity.setDescription(dto.getDescription());
        entity.setFullDescription(dto.getFullDescription());
        entity.setPrice(dto.getPrice());
        return toDto(bookConditionRepository.save(entity));
    }

    public void delete(Integer ranks) {
        BookCondition entity = findEntity(ranks);
        bookConditionRepository.delete(entity);
    }

    private BookCondition findEntity(Integer ranks) {
        return bookConditionRepository.findById(ranks)
                .orElseThrow(() -> new ResourceNotFoundException("BookCondition not found with id: " + ranks));
    }

    private BookConditionDto toDto(BookCondition entity) {
        BookConditionDto dto = new BookConditionDto();
        dto.setRanks(entity.getRanks());
        dto.setDescription(entity.getDescription());
        dto.setFullDescription(entity.getFullDescription());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
