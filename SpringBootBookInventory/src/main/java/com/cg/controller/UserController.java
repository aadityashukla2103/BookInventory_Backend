package com.cg.controller;

import com.cg.dto.UserDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<UserDto> getAll() { return userService.getAll(); }

    @GetMapping("/{userId}")
    public UserDto getById(@PathVariable Integer userId) { return userService.getById(userId); }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable Integer userId, @RequestBody UserDto dto) {
        return userService.update(userId, dto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}