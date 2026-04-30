package com.cg.service;

import com.cg.dto.UserRequestDto;
import com.cg.dto.UserResponseDto;
import com.cg.entity.PermRole;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import com.cg.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PermRoleRepository permRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PermRoleRepository permRoleRepository) {
        this.userRepository = userRepository;
        this.permRoleRepository = permRoleRepository;
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public UserResponseDto getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return toDto(findEntity(id));
    }

    public UserResponseDto create(UserRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }

        User user = new User();
        apply(dto, user);

        return toDto(userRepository.save(user));
    }

    public UserResponseDto update(Integer id, UserRequestDto dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("User ID and data cannot be null");
        }

        User user = findEntity(id);
        apply(dto, user);

        return toDto(userRepository.save(user));
    }

    public void delete(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        userRepository.delete(findEntity(id));
    }

    private User findEntity(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    private void apply(UserRequestDto dto, User user) {

        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserName(dto.getUserName());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRoleNumber() != null) {
            PermRole role = permRoleRepository.findById(dto.getRoleNumber())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Role not found with id: " + dto.getRoleNumber()
                            ));

            user.setRole(role);
        } else {
            user.setRole(null);
        }
    }

    private UserResponseDto toDto(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setUserID(user.getUserID());
        dto.setLastName(user.getLastName());
        dto.setFirstName(user.getFirstName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setUserName(user.getUserName());

        dto.setRoleNumber(
                user.getRole() != null
                        ? user.getRole().getRoleNumber()
                        : null
        );

        return dto;
    }
}