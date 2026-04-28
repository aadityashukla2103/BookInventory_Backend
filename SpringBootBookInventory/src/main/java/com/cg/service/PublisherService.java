package com.cg.service;

import com.cg.dto.PublisherDto;
import com.cg.entity.Publisher;
import com.cg.entity.State;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PublisherRepository;
import com.cg.repo.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;
    private final StateRepository stateRepository;

    public PublisherService(PublisherRepository publisherRepository, StateRepository stateRepository) {
        this.publisherRepository = publisherRepository;
        this.stateRepository = stateRepository;
    }

    public List<PublisherDto> getAll() { return publisherRepository.findAll().stream().map(this::toDto).toList(); }
    public PublisherDto getById(Integer id) { return toDto(findEntity(id)); }

    public PublisherDto create(PublisherDto dto) {
        Publisher entity = new Publisher();
        entity.setPublisherId(dto.getPublisherId());
        apply(dto, entity);
        return toDto(publisherRepository.save(entity));
    }

    public PublisherDto update(Integer id, PublisherDto dto) {
        Publisher entity = findEntity(id);
        apply(dto, entity);
        return toDto(publisherRepository.save(entity));
    }

    public void delete(Integer id) { publisherRepository.delete(findEntity(id)); }

    private Publisher findEntity(Integer id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + id));
    }

    private void apply(PublisherDto dto, Publisher entity) {
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        if (dto.getStateCode() != null) {
            State state = stateRepository.findById(dto.getStateCode())
                    .orElseThrow(() -> new ResourceNotFoundException("State not found with id: " + dto.getStateCode()));
            entity.setState(state);
        } else {
            entity.setState(null);
        }
    }

    private PublisherDto toDto(Publisher entity) {
        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(entity.getPublisherId());
        dto.setName(entity.getName());
        dto.setCity(entity.getCity());
        dto.setStateCode(entity.getState() != null ? entity.getState().getStateCode() : null);
        return dto;
    }
}