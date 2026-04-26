package com.cg.service;

import com.cg.dto.StateDto;
import com.cg.entity.State;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.StateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {
    private final StateRepository repo;
    public StateService(StateRepository repo) { this.repo = repo; }
    public List<StateDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public StateDto get(String code){ return toDto(repo.findById(code).orElseThrow(() -> new ResourceNotFoundException("State not found"))); }
    public StateDto save(StateDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(String code){ repo.deleteById(code); }
    private StateDto toDto(State s){ return new StateDto(s.getStateCode(), s.getStateName()); }
    private State toEntity(StateDto d){ State s=new State(); s.setStateCode(d.stateCode()); s.setStateName(d.stateName()); return s; }
}
