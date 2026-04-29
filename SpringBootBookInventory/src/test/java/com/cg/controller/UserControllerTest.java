```java id="v7n2kp"
package com.cg.controller;

import com.cg.dto.UserDto;
import com.cg.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto dto;

    @BeforeEach
    void setUp() {
        dto = new UserDto();
        dto.setUserID(1);
        dto.setFirstName("Aashish");
        dto.setLastName("Tomar");
        dto.setUserName("aashish");
    }

    @Test
    void testGetAll() {
        when(userService.getAll()).thenReturn(java.util.List.of(dto));

        var result = userController.getAll();

        assertEquals(1, result.size());
        verify(userService, times(1)).getAll();
    }

    @Test
    void testGetById() {
        when(userService.getById(1)).thenReturn(dto);

        UserDto result = userController.getById(1);

        assertEquals(1, result.getUserID());
        assertEquals("Aashish", result.getFirstName());
    }

    @Test
    void testCreate() {
        when(userService.create(dto)).thenReturn(dto);

        ResponseEntity<UserDto> response = userController.create(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        when(userService.update(1, dto)).thenReturn(dto);

        UserDto result = userController.update(1, dto);

        assertEquals("Tomar", result.getLastName());
        verify(userService, times(1)).update(1, dto);
    }

    @Test
    void testDelete() {
        ResponseEntity<Void> response = userController.delete(1);

        verify(userService, times(1)).delete(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}
```
