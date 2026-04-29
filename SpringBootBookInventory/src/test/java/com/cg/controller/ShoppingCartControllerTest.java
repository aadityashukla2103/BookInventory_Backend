```java id="m5r2tz"
package com.cg.controller;

import com.cg.dto.ShoppingCartDto;
import com.cg.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    private ShoppingCartDto dto;

    @BeforeEach
    void setUp() {
        dto = new ShoppingCartDto();
        dto.setUserID(1);
        dto.setIsbn("12345");
    }

    @Test
    void testGetAll() {
        when(shoppingCartService.getAll()).thenReturn(List.of(dto));

        List<ShoppingCartDto> result = shoppingCartController.getAll();

        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getIsbn());
        verify(shoppingCartService, times(1)).getAll();
    }

    @Test
    void testGetById() {
        when(shoppingCartService.getById(1, "12345")).thenReturn(dto);

        ShoppingCartDto result = shoppingCartController.getById(1, "12345");

        assertEquals(1, result.getUserID());
        assertEquals("12345", result.getIsbn());
    }

    @Test
    void testCreate() {
        when(shoppingCartService.create(dto)).thenReturn(dto);

        ResponseEntity<ShoppingCartDto> response = shoppingCartController.create(dto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testUpdate() {
        when(shoppingCartService.update(1, "12345", dto)).thenReturn(dto);

        ShoppingCartDto result = shoppingCartController.update(1, "12345", dto);

        assertEquals("12345", result.getIsbn());
        verify(shoppingCartService, times(1))
                .update(1, "12345", dto);
    }

    @Test
    void testDelete() {
        ResponseEntity<Void> response =
                shoppingCartController.delete(1, "12345");

        verify(shoppingCartService, times(1))
                .delete(1, "12345");

        assertEquals(204, response.getStatusCodeValue());
    }
}
```
