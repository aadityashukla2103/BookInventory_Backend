package com.cg.service;

import com.cg.dto.PermRoleDto;
import com.cg.entity.PermRole;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final PermRoleRepository repo;

    public RoleService(PermRoleRepository repo) {
        this.repo = repo;
    }

    public List<PermRoleDto> getAll() { return repo.findAll().stream().map(this::toDto).toList(); }
    public PermRoleDto get(Integer id) { return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"))); }
    public PermRoleDto save(PermRoleDto role) { return toDto(repo.save(toEntity(role))); }
    public void delete(Integer id) { repo.deleteById(id); }

    private PermRoleDto toDto(PermRole role){ return new PermRoleDto(role.getRoleNumber(), role.getPermRole()); }
    private PermRole toEntity(PermRoleDto dto){ PermRole role = new PermRole(); role.setRoleNumber(dto.roleNumber()); role.setPermRole(dto.permRole()); return role; }
}
