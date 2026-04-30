package com.cg.service;

import com.cg.dto.StateDto;
import com.cg.entity.State;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<StateDto> getAll() {
        return stateRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public StateDto getById(String stateCode) {
        if (stateCode == null || stateCode.isBlank()) {
            throw new IllegalArgumentException("State code cannot be null or empty");
        }

        State entity = findEntity(stateCode);
        return toDto(entity);
    }

    public StateDto create(StateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("State data cannot be null");
        }

        State entity = new State();
        entity.setStateCode(dto.getStateCode());
        entity.setStateName(dto.getStateName());

        return toDto(stateRepository.save(entity));
    }

    public StateDto update(String stateCode, StateDto dto) {
        if (stateCode == null || stateCode.isBlank() || dto == null) {
            throw new IllegalArgumentException("Invalid input for update");
        }

        State entity = findEntity(stateCode);
        entity.setStateName(dto.getStateName());

        return toDto(stateRepository.save(entity));
    }

    public void delete(String stateCode) {
        if (stateCode == null || stateCode.isBlank()) {
            throw new IllegalArgumentException("State code cannot be null or empty");
        }

        State entity = findEntity(stateCode);
        stateRepository.delete(entity);
    }

    private State findEntity(String stateCode) {
        return stateRepository.findById(stateCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("State not found with id: " + stateCode));
    }

    private StateDto toDto(State entity) {
        StateDto dto = new StateDto();
        dto.setStateCode(entity.getStateCode());
        dto.setStateName(entity.getStateName());
        return dto;
    }
}