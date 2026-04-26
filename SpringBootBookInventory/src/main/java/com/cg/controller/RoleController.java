package com.cg.controller;

import com.cg.dto.PermRoleDto;
import com.cg.service.RoleService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService service;
    public RoleController(RoleService service){ this.service=service; }
    @GetMapping public List<PermRoleDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public PermRoleDto one(@PathVariable Integer id){ return service.get(id); }
    @PostMapping public PermRoleDto create(@RequestBody PermRoleDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public PermRoleDto update(@PathVariable Integer id,@RequestBody PermRoleDto dto){ return service.save(new PermRoleDto(id,dto.permRole())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
