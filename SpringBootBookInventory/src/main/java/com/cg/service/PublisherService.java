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
    private final PublisherRepository repo;
    private final StateRepository stateRepository;

    public PublisherService(PublisherRepository repo, StateRepository stateRepository) {
        this.repo = repo;
        this.stateRepository = stateRepository;
    }

    public List<PublisherDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public PublisherDto get(Integer id){ return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publisher not found"))); }
    public PublisherDto save(PublisherDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(Integer id){ repo.deleteById(id); }

    private PublisherDto toDto(Publisher p){ return new PublisherDto(p.getPublisherId(), p.getName(), p.getCity(), p.getState() == null ? null : p.getState().getStateCode()); }
    private Publisher toEntity(PublisherDto d){ Publisher p = new Publisher(); p.setPublisherId(d.publisherId()); p.setName(d.name()); p.setCity(d.city()); if (d.stateCode()!=null) { State s=stateRepository.findById(d.stateCode()).orElseThrow(() -> new ResourceNotFoundException("State not found")); p.setState(s);} return p; }
}
