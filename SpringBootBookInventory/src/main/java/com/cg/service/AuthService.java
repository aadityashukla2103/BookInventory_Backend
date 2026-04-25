package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.cg.repo.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    public User register(User user) {
        if (user.getUserName() == null || user.getPassword() == null) {
            throw new BadRequestException("Username or Password missing");
        }
        return repo.save(user);
    }

    public User findByUsername(String username) {
        return repo.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
