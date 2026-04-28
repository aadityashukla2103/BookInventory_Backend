package com.cg.service;

import com.cg.dto.UserDto;
import com.cg.entity.PermRole;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import com.cg.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PermRoleRepository permRoleRepository;

    public UserService(UserRepository userRepository, PermRoleRepository permRoleRepository) {
        this.userRepository = userRepository;
        this.permRoleRepository = permRoleRepository;
    }

    public List<UserDto> getAll() { return userRepository.findAll().stream().map(this::toDto).toList(); }
    public UserDto getById(Integer id) { return toDto(findEntity(id)); }

    public UserDto create(UserDto dto) {
        User entity = new User();
        apply(dto, entity);
        return toDto(userRepository.save(entity));
    }

    public UserDto update(Integer id, UserDto dto) {
        User entity = findEntity(id);
        apply(dto, entity);
        return toDto(userRepository.save(entity));
    }

    public void delete(Integer id) { userRepository.delete(findEntity(id)); }

    private User findEntity(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private void apply(UserDto dto, User entity) {
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUserName(dto.getUserName());
        entity.setPassword(dto.getPassword());
        if (dto.getRoleNumber() != null) {
            PermRole role = permRoleRepository.findById(dto.getRoleNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("PermRole not found with id: " + dto.getRoleNumber()));
            entity.setRole(role);
        } else {
            entity.setRole(null);
        }
    }

    private UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setUserID(entity.getUserID());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setUserName(entity.getUserName());
        dto.setPassword(entity.getPassword());
        dto.setRoleNumber(entity.getRole() != null ? entity.getRole().getRoleNumber() : null);
        return dto;
    }
}