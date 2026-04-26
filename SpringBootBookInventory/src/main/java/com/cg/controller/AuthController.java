package com.cg.controller;

import com.cg.dto.UserDto;
import com.cg.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/users/{username}")
    public UserDto findByUsername(@PathVariable String username) {
        return authService.findByUsername(username);
    }
}
