package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repo;

    public List<PermRole> getAll() {
        return repo.findAll();
    }

    public PermRole save(PermRole role) {
        if (role.getPermRole() == null) {
            throw new BadRequestException("Role name required");
        }
        return repo.save(role);
    }

    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Role not found");
        }
        repo.deleteById(id);
    }
}