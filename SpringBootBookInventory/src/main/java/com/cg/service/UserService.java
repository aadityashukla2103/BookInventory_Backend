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
        return toDto(findEntity(id));
    }

   
    public UserResponseDto create(UserRequestDto dto) {
        User entity = new User();
        apply(dto, entity);
        return toDto(userRepository.save(entity));
    }

    
    public UserResponseDto update(Integer id, UserRequestDto dto) {
        User entity = findEntity(id);
        apply(dto, entity);
        return toDto(userRepository.save(entity));
    }

    
    public void delete(Integer id) {
        userRepository.delete(findEntity(id));
    }

    
    private User findEntity(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));
    }

   
    private void apply(UserRequestDto dto, User entity) {

        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUserName(dto.getUserName());

        
        if (dto.getPassword() != null &&
            !dto.getPassword().isBlank()) {

            entity.setPassword(
                    passwordEncoder.encode(dto.getPassword()));
        }

        
        if (dto.getRoleNumber() != null) {

            PermRole role = permRoleRepository
                    .findById(dto.getRoleNumber())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "PermRole not found with id: "
                                            + dto.getRoleNumber()));

            entity.setRole(role);

        } else {
            entity.setRole(null);
        }
    }

    
    private UserResponseDto toDto(User entity) {

        UserResponseDto dto = new UserResponseDto();

        dto.setUserID(entity.getUserID());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setUserName(entity.getUserName());

        dto.setRoleNumber(
                entity.getRole() != null
                        ? entity.getRole().getRoleNumber()
                        : null
        );

        return dto;
    }
}