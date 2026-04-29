package com.cg.controller;

import com.cg.dto.PermRoleDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.PermRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perm-roles")
public class PermRoleController {

    private final PermRoleService permRoleService;

    public PermRoleController(PermRoleService permRoleService) {
        this.permRoleService = permRoleService;
    }

    @GetMapping
    public List<PermRoleDto> getAll() {
        return permRoleService.getAll();
    }

    @GetMapping("/{roleNumber}")
    public PermRoleDto getById(@PathVariable Integer roleNumber) {
        return permRoleService.getById(roleNumber);
    }

    @PostMapping
    public ResponseEntity<PermRoleDto> create(@RequestBody PermRoleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permRoleService.create(dto));
    }

    @PutMapping("/{roleNumber}")
    public PermRoleDto update(@PathVariable Integer roleNumber, @RequestBody PermRoleDto dto) {
        return permRoleService.update(roleNumber, dto);
    }

    @DeleteMapping("/{roleNumber}")
    public ResponseEntity<Void> delete(@PathVariable Integer roleNumber) {
        permRoleService.delete(roleNumber);
        return ResponseEntity.noContent().build();
    }

}