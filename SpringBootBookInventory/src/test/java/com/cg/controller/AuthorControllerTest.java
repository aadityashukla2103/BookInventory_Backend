package com.cg.controller;

import com.cg.dto.AuthorDto;
import com.cg.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @Test
    void getAllShouldReturnList() {

        AuthorDto a1 = new AuthorDto();
        a1.setAuthorID(1);
        a1.setFirstName("Robert");
        a1.setLastName("Martin");

        AuthorDto a2 = new AuthorDto();
        a2.setAuthorID(2);
        a2.setFirstName("James");
        a2.setLastName("Gosling");

        when(authorService.getAll()).thenReturn(Arrays.asList(a1, a2));

        List<AuthorDto> result = authorController.getAll();

        assertEquals(2, result.size());
        assertEquals("Robert", result.get(0).getFirstName());
        assertEquals("James", result.get(1).getFirstName());

        verify(authorService).getAll();
    }

    @Test
    void getByIdShouldReturnAuthor() {

        AuthorDto dto = new AuthorDto();
        dto.setAuthorID(1);
        dto.setFirstName("Robert");
        dto.setLastName("Martin");

        when(authorService.getById(1)).thenReturn(dto);

        AuthorDto result = authorController.getById(1);

        assertEquals(1, result.getAuthorID());
        assertEquals("Robert", result.getFirstName());
        assertEquals("Martin", result.getLastName());

        verify(authorService).getById(1);
    }

    @Test
    void createShouldReturnCreatedResponse() {

        AuthorDto request = new AuthorDto();
        request.setAuthorID(1);
        request.setFirstName("Robert");

        AuthorDto response = new AuthorDto();
        response.setAuthorID(1);
        response.setFirstName("Robert");

        when(authorService.create(request)).thenReturn(response);

        ResponseEntity<AuthorDto> result = authorController.create(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getAuthorID());

        verify(authorService).create(request);
    }

    @Test
    void updateShouldReturnUpdatedAuthor() {

        AuthorDto request = new AuthorDto();
        request.setFirstName("Updated");

        AuthorDto response = new AuthorDto();
        response.setAuthorID(1);
        response.setFirstName("Updated");

        when(authorService.update(1, request)).thenReturn(response);

        AuthorDto result = authorController.update(1, request);

        assertEquals(1, result.getAuthorID());
        assertEquals("Updated", result.getFirstName());

        verify(authorService).update(1, request);
    }

    @Test
    void deleteShouldReturnNoContent() {

        doNothing().when(authorService).delete(1);

        ResponseEntity<Void> result = authorController.delete(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());

        verify(authorService).delete(1);
    }
}