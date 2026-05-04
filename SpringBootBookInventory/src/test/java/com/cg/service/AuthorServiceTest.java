package com.cg.service;

import com.cg.dto.AuthorDto;
import com.cg.entity.Author;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

	@Mock
	private AuthorRepository authorRepository;

	@InjectMocks
	private AuthorService authorService;

	@Test
	void getAllShouldReturnList() {

		Author a1 = new Author();
		a1.setAuthorID(1);
		a1.setFirstName("Robert");
		a1.setLastName("Martin");
		a1.setPhoto("img1");

		Author a2 = new Author();
		a2.setAuthorID(2);
		a2.setFirstName("James");
		a2.setLastName("Gosling");
		a2.setPhoto("img2");

		when(authorRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

		List<AuthorDto> result = authorService.getAll();

		assertEquals(2, result.size());
		assertEquals("Robert", result.get(0).getFirstName());
		assertEquals("James", result.get(1).getFirstName());

		verify(authorRepository).findAll();
	}

	@Test
	void getByIdShouldReturnAuthor() {

		Author author = new Author();
		author.setAuthorID(1);
		author.setFirstName("Robert");
		author.setLastName("Martin");
		author.setPhoto("img");

		when(authorRepository.findById(1)).thenReturn(Optional.of(author));

		AuthorDto result = authorService.getById(1);

		assertEquals(1, result.getAuthorID());
		assertEquals("Robert", result.getFirstName());
		assertEquals("Martin", result.getLastName());

		verify(authorRepository).findById(1);
	}

	@Test
	void getByIdShouldThrowWhenNotFound() {

		when(authorRepository.findById(404)).thenReturn(Optional.empty());

		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> authorService.getById(404));

		assertEquals("Author not found with id: 404", ex.getMessage());
	}

	@Test
	void createShouldReturnMappedDto() {

		AuthorDto request = new AuthorDto();
		request.setAuthorID(1);
		request.setFirstName("Robert");
		request.setLastName("Martin");
		request.setPhoto("img");

		when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> {
			Author saved = invocation.getArgument(0);
			saved.setAuthorID(1);
			return saved;
		});

		AuthorDto result = authorService.create(request);

		assertEquals(1, result.getAuthorID());
		assertEquals("Robert", result.getFirstName());
		assertEquals("Martin", result.getLastName());
		assertEquals("img", result.getPhoto());

		verify(authorRepository).save(any(Author.class));
	}

	@Test
	void updateShouldModifyAndReturnDto() {

		Author existing = new Author();
		existing.setAuthorID(1);
		existing.setFirstName("Old");
		existing.setLastName("Name");
		existing.setPhoto("old");

		AuthorDto request = new AuthorDto();
		request.setFirstName("New");
		request.setLastName("Martin");
		request.setPhoto("newimg");

		when(authorRepository.findById(1)).thenReturn(Optional.of(existing));
		when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));

		AuthorDto result = authorService.update(1, request);

		assertEquals(1, result.getAuthorID());
		assertEquals("New", result.getFirstName());
		assertEquals("Martin", result.getLastName());
		assertEquals("newimg", result.getPhoto());

		verify(authorRepository).findById(1);
		verify(authorRepository).save(existing);
	}

	@Test
	void updateShouldThrowWhenNotFound() {

		AuthorDto request = new AuthorDto();

		when(authorRepository.findById(99)).thenReturn(Optional.empty());

		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
				() -> authorService.update(99, request));

		assertEquals("Author not found with id: 99", ex.getMessage());

		verify(authorRepository).findById(99);
		verify(authorRepository, never()).save(any());
	}

	@Test
	void deleteShouldLoadAndDeleteEntity() {

		Author author = new Author();
		author.setAuthorID(7);

		when(authorRepository.findById(7)).thenReturn(Optional.of(author));

		authorService.delete(7);

		verify(authorRepository).findById(7);
		verify(authorRepository).delete(author);
	}

	@Test
	void deleteShouldThrowWhenNotFound() {

		when(authorRepository.findById(10)).thenReturn(Optional.empty());

		ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> authorService.delete(10));

		assertEquals("Author not found with id: 10", ex.getMessage());

		verify(authorRepository).findById(10);
		verify(authorRepository, never()).delete(any());
	}
}
