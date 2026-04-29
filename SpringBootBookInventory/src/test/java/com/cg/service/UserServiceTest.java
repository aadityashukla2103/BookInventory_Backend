package com.cg.service;

import com.cg.dto.UserDto;
import com.cg.entity.PermRole;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import com.cg.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

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
    private UserDto dto;
    private PermRole role;

    @BeforeEach
    void setUp() throws Exception {

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

        dto = new UserDto();
        dto.setFirstName("Aashish");
        dto.setLastName("Tomar");
        dto.setPhoneNumber("9876543210");
        dto.setUserName("aashish");
        dto.setPassword("12345");
        dto.setRoleNumber(1);

        Field field = UserService.class.getDeclaredField("passwordEncoder");
        field.setAccessible(true);
        field.set(userService, passwordEncoder);
    }

    @Test
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDto> result = userService.getAll();

        assertEquals(1, result.size());
        assertEquals("Aashish", result.get(0).getFirstName());
    }

    @Test
    void testGetById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDto result = userService.getById(1);

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

        UserDto result = userService.create(dto);

        assertEquals("Aashish", result.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdate() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("12345")).thenReturn("encoded123");
        when(permRoleRepository.findById(1)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.update(1, dto);

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
                () -> userService.create(dto));
    }

    @Test
    void testUpdate_UserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.update(1, dto));
    }
}