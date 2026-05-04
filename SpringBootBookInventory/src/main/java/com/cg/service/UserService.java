package com.cg.service;

import com.cg.dto.UserRequestDto;
import com.cg.dto.UserResponseDto;
import com.cg.entity.PermRole;
import com.cg.entity.User;
import com.cg.exception.DuplicateResourceException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import com.cg.repo.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private static final Integer DEFAULT_ROLE_NUMBER = 2;

    private final UserRepository userRepository;
    private final PermRoleRepository permRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PermRoleRepository permRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permRoleRepository = permRoleRepository;
        this.passwordEncoder = passwordEncoder;
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

        validateUniqueUserName(dto.getUserName(), null);

        User user = new User();
        apply(dto, user, DEFAULT_ROLE_NUMBER);

        return toDto(userRepository.save(user));
    }

    public UserResponseDto update(Integer id, UserRequestDto dto) {
        if (id == null || dto == null) {
            throw new IllegalArgumentException("User ID and data cannot be null");
        }

        User user = findEntity(id);
        validateUniqueUserName(dto.getUserName(), id);
        apply(dto, user, dto.getRoleNumber());

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

    private void validateUniqueUserName(String userName, Integer currentUserId) {
        String normalizedUserName = normalize(userName);
        if (normalizedUserName == null) {
            return;
        }

        userRepository.findByUserName(normalizedUserName)
                .filter(existing -> !Objects.equals(existing.getUserID(), currentUserId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Username already exists: " + normalizedUserName);
                });
    }

    private void apply(UserRequestDto dto, User user, Integer fallbackRoleNumber) {

        user.setLastName(normalize(dto.getLastName()));
        user.setFirstName(normalize(dto.getFirstName()));
        user.setPhoneNumber(normalize(dto.getPhoneNumber()));
        user.setUserName(normalize(dto.getUserName()));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Integer roleNumber = dto.getRoleNumber() != null ? dto.getRoleNumber() : fallbackRoleNumber;
        if (roleNumber != null) {
            PermRole role = permRoleRepository.findById(roleNumber)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Role not found with id: " + roleNumber
                            ));

            user.setRole(role);
        }
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
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
