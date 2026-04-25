package com.cg.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.cg.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User save(User user) {
        if (user.getUserName() == null) {
            throw new BadRequestException("Username required");
        }
        return repo.save(user);
    }

    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        repo.deleteById(id);
    }
}