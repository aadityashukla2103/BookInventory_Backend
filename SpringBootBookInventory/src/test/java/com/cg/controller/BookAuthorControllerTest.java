package com.cg.controller;

import com.cg.dto.BookAuthorDto;
import com.cg.service.BookAuthorService;
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
class BookAuthorControllerTest {

	@Mock
	private BookAuthorService bookAuthorService;

	@InjectMocks
	private BookAuthorController bookAuthorController;

	@Test
	void getAllShouldReturnList() {

		BookAuthorDto d1 = new BookAuthorDto();
		d1.setIsbn("111");
		d1.setAuthorID(1);
		d1.setPrimaryAuthor("Y");

		BookAuthorDto d2 = new BookAuthorDto();
		d2.setIsbn("222");
		d2.setAuthorID(2);
		d2.setPrimaryAuthor("N");

		when(bookAuthorService.getAll()).thenReturn(Arrays.asList(d1, d2));

		List<BookAuthorDto> result = bookAuthorController.getAll();

		assertEquals(2, result.size());
		assertEquals("111", result.get(0).getIsbn());
		assertEquals(1, result.get(0).getAuthorID());
		assertEquals("222", result.get(1).getIsbn());

		verify(bookAuthorService).getAll();
	}

	@Test
	void getByIdShouldReturnRecord() {

		BookAuthorDto dto = new BookAuthorDto();
		dto.setIsbn("123");
		dto.setAuthorID(7);
		dto.setPrimaryAuthor("Y");

		when(bookAuthorService.getById("123", 7)).thenReturn(dto);

		BookAuthorDto result = bookAuthorController.getById("123", 7);

		assertEquals("123", result.getIsbn());
		assertEquals(7, result.getAuthorID());
		assertEquals("Y", result.getPrimaryAuthor());

		verify(bookAuthorService).getById("123", 7);
	}

	@Test
	void createShouldReturnCreatedResponse() {

		BookAuthorDto request = new BookAuthorDto();
		request.setIsbn("123");
		request.setAuthorID(1);
		request.setPrimaryAuthor("Y");

		BookAuthorDto response = new BookAuthorDto();
		response.setIsbn("123");
		response.setAuthorID(1);
		response.setPrimaryAuthor("Y");

		when(bookAuthorService.create(request)).thenReturn(response);

		ResponseEntity<BookAuthorDto> result = bookAuthorController.create(request);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertNotNull(result.getBody());
		assertEquals("123", result.getBody().getIsbn());
		assertEquals(1, result.getBody().getAuthorID());

		verify(bookAuthorService).create(request);
	}

	@Test
	void updateShouldReturnUpdatedRecord() {

		BookAuthorDto request = new BookAuthorDto();
		request.setPrimaryAuthor("N");

		BookAuthorDto response = new BookAuthorDto();
		response.setIsbn("123");
		response.setAuthorID(1);
		response.setPrimaryAuthor("N");

		when(bookAuthorService.update("123", 1, request)).thenReturn(response);

		BookAuthorDto result = bookAuthorController.update("123", 1, request);

		assertEquals("123", result.getIsbn());
		assertEquals(1, result.getAuthorID());
		assertEquals("N", result.getPrimaryAuthor());

		verify(bookAuthorService).update("123", 1, request);
	}

	@Test
	void deleteShouldReturnNoContent() {

		doNothing().when(bookAuthorService).delete("123", 1);

		ResponseEntity<Void> result = bookAuthorController.delete("123", 1);

		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
		assertNull(result.getBody());

		verify(bookAuthorService).delete("123", 1);
	}
}