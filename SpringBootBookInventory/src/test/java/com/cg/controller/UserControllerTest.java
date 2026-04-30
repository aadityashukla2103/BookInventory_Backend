package com.cg.controller;

import com.cg.dto.UserRequestDto;
import com.cg.dto.UserResponseDto;
import com.cg.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRequestDto requestDto;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {

        requestDto = new UserRequestDto();
        requestDto.setFirstName("Aashish");
        requestDto.setLastName("Tomar");
        requestDto.setUserName("aashish");
        requestDto.setPassword("12345");
        requestDto.setRoleNumber(1);

        responseDto = new UserResponseDto();
        responseDto.setUserID(1);
        responseDto.setFirstName("Aashish");
        responseDto.setLastName("Tomar");
        responseDto.setUserName("aashish");
        responseDto.setRoleNumber(1);
    }

    @Test
    void testGetAll() {

        when(userService.getAll()).thenReturn(List.of(responseDto));

        var result = userController.getAll();

        assertEquals(1, result.size());
        verify(userService, times(1)).getAll();
    }

    @Test
    void testGetById() {

        when(userService.getById(1)).thenReturn(responseDto);

        UserResponseDto result = userController.getById(1);

        assertEquals(1, result.getUserID());
        assertEquals("Aashish", result.getFirstName());
    }

    @Test
    void testCreate() {

        when(userService.create(requestDto)).thenReturn(responseDto);

        ResponseEntity<UserResponseDto> response =
                userController.create(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void testUpdate() {

        when(userService.update(1, requestDto)).thenReturn(responseDto);

        UserResponseDto result =
                userController.update(1, requestDto);

        assertEquals("Tomar", result.getLastName());
        verify(userService, times(1)).update(1, requestDto);
    }

    @Test
    void testDelete() {

        ResponseEntity<Void> response =
                userController.delete(1);

        verify(userService, times(1)).delete(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}