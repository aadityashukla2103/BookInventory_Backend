package com.cg.controller;

import com.cg.dto.UserDto;
import com.cg.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service){ this.service = service; }
    @GetMapping public List<UserDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public UserDto one(@PathVariable Integer id){ return service.getById(id); }
    @PostMapping public UserDto create(@RequestBody UserDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public UserDto update(@PathVariable Integer id, @RequestBody UserDto dto){ return service.save(new UserDto(id,dto.firstName(),dto.lastName(),dto.phoneNumber(),dto.userName(),dto.password(),dto.roleNumber())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
