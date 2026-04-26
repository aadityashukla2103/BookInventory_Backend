package com.cg.service;

import com.cg.dto.UserDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDto findByUsername(String username) {
        return repo.findByUserName(username)
                .map(u -> new UserDto(u.getUserID(), u.getFirstName(), u.getLastName(), u.getPhoneNumber(), u.getUserName(), u.getPassword(), u.getRole() == null ? null : u.getRole().getRoleNumber()))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
