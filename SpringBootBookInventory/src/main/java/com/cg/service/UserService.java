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

    private final UserRepository repo;
    private final PermRoleRepository roleRepository;

    public UserService(UserRepository repo, PermRoleRepository roleRepository) {
        this.repo = repo;
        this.roleRepository = roleRepository;
    }

    public List<UserDto> getAll() { return repo.findAll().stream().map(this::toDto).toList(); }

    public UserDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")));
    }

    public UserDto save(UserDto user) {
        return toDto(repo.save(toEntity(user)));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    private UserDto toDto(User user){ return new UserDto(user.getUserID(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getUserName(), user.getPassword(), user.getRole()==null?null:user.getRole().getRoleNumber()); }
    private User toEntity(UserDto dto){ User user = new User(); user.setUserID(dto.userID()); user.setFirstName(dto.firstName()); user.setLastName(dto.lastName()); user.setPhoneNumber(dto.phoneNumber()); user.setUserName(dto.userName()); user.setPassword(dto.password()); if(dto.roleNumber()!=null){ PermRole role=roleRepository.findById(dto.roleNumber()).orElseThrow(() -> new ResourceNotFoundException("Role not found")); user.setRole(role);} return user; }
}
