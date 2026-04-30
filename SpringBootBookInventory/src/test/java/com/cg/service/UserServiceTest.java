package com.cg.service;

import com.cg.dto.UserRequestDto;
import com.cg.dto.UserResponseDto;
import com.cg.entity.PermRole;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import com.cg.repo.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermRoleRepository permRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private PermRole role;
    private UserRequestDto requestDto;

    @BeforeEach
    void setUp() {

        role = new PermRole();
        role.setRoleNumber(1);

        user = new User();
        user.setUserID(1);
        user.setFirstName("Aashish");
        user.setLastName("Tomar");
        user.setPhoneNumber("9876543210");
        user.setUserName("aashish");
        user.setPassword("encoded123");
        user.setRole(role);

        requestDto = new UserRequestDto();
        requestDto.setFirstName("Aashish");
        requestDto.setLastName("Tomar");
        requestDto.setPhoneNumber("9876543210");
        requestDto.setUserName("aashish");
        requestDto.setPassword("12345");
        requestDto.setRoleNumber(1);
    }

    @Test
    void testGetAll() {

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserResponseDto> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals("Aashish", result.get(0).getFirstName());
    }

    @Test
    void testGetById() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserResponseDto result = userService.getById(1);

        assertEquals("Aashish", result.getFirstName());
    }

    @Test
    void testGetById_NotFound() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getById(1));
    }

    @Test
    void testCreate() {

        when(passwordEncoder.encode("12345")).thenReturn("encoded123");
        when(permRoleRepository.findById(1)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto result = userService.create(requestDto);

        assertEquals("Aashish", result.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("12345")).thenReturn("encoded123");
        when(permRoleRepository.findById(1)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDto result = userService.update(1, requestDto);

        assertEquals("Tomar", result.getLastName());
    }

    @Test
    void testDelete() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.delete(1);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testCreate_RoleNotFound() {

        when(passwordEncoder.encode("12345")).thenReturn("encoded123");
        when(permRoleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.create(requestDto));
    }

    @Test
    void testUpdate_UserNotFound() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.update(1, requestDto));
    }
}