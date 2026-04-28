package com.cg.service;

import com.cg.dto.PermRoleDto;
import com.cg.entity.PermRole;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermRoleService {

    private final PermRoleRepository permRoleRepository;

    public PermRoleService(PermRoleRepository permRoleRepository) {
        this.permRoleRepository = permRoleRepository;
    }

    public List<PermRoleDto> getAll() {
        return permRoleRepository.findAll().stream().map(this::toDto).toList();
    }

    public PermRoleDto getById(Integer roleNumber) {
        PermRole entity = findEntity(roleNumber);
        return toDto(entity);
    }

    public PermRoleDto create(PermRoleDto dto) {
        PermRole entity = new PermRole();
        entity.setRoleNumber(dto.getRoleNumber());
        entity.setPermRole(dto.getPermRole());
        return toDto(permRoleRepository.save(entity));
    }

    public PermRoleDto update(Integer roleNumber, PermRoleDto dto) {
        PermRole entity = findEntity(roleNumber);
        entity.setPermRole(dto.getPermRole());
        return toDto(permRoleRepository.save(entity));
    }

    public void delete(Integer roleNumber) {
        PermRole entity = findEntity(roleNumber);
        permRoleRepository.delete(entity);
    }

    private PermRole findEntity(Integer roleNumber) {
        return permRoleRepository.findById(roleNumber)
                .orElseThrow(() -> new ResourceNotFoundException("PermRole not found with id: " + roleNumber));
    }

    private PermRoleDto toDto(PermRole entity) {
        PermRoleDto dto = new PermRoleDto();
        dto.setRoleNumber(entity.getRoleNumber());
        dto.setPermRole(entity.getPermRole());
        return dto;
    }
}