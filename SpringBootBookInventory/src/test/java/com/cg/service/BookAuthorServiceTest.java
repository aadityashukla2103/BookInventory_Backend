package com.cg.service;

import com.cg.dto.BookAuthorDto;
import com.cg.entity.Author;
import com.cg.entity.Book;
import com.cg.entity.BookAuthor;
import com.cg.entity.BookAuthorId;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.AuthorRepository;
import com.cg.repo.BookAuthorRepository;
import com.cg.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookAuthorServiceTest {

    @Mock
    private BookAuthorRepository bookAuthorRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookAuthorService bookAuthorService;

    @Test
    void getAllShouldReturnList() {

        BookAuthor ba1 = new BookAuthor();
        ba1.setId(new BookAuthorId("111", 1));
        ba1.setPrimaryAuthor("Y");

        BookAuthor ba2 = new BookAuthor();
        ba2.setId(new BookAuthorId("222", 2));
        ba2.setPrimaryAuthor("N");

        when(bookAuthorRepository.findAll()).thenReturn(Arrays.asList(ba1, ba2));

        List<BookAuthorDto> result = bookAuthorService.getAll();

        assertEquals(2, result.size());
        assertEquals("111", result.get(0).getIsbn());
        assertEquals(1, result.get(0).getAuthorID());
        assertEquals("222", result.get(1).getIsbn());

        verify(bookAuthorRepository).findAll();
    }

    @Test
    void getByIdShouldReturnRecord() {

        BookAuthor entity = new BookAuthor();
        entity.setId(new BookAuthorId("123", 7));
        entity.setPrimaryAuthor("Y");

        when(bookAuthorRepository.findById(any(BookAuthorId.class)))
                .thenReturn(Optional.of(entity));

        BookAuthorDto result = bookAuthorService.getById("123", 7);

        assertEquals("123", result.getIsbn());
        assertEquals(7, result.getAuthorID());
        assertEquals("Y", result.getPrimaryAuthor());
    }

    @Test
    void getByIdShouldThrowWhenNotFound() {

        when(bookAuthorRepository.findById(any(BookAuthorId.class)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookAuthorService.getById("999", 99));

        assertEquals(
                "BookAuthor not found for isbn 999 and author 99",
                ex.getMessage()
        );
    }

    @Test
    void createShouldReturnDto() {

        Book book = new Book();
        Author author = new Author();

        BookAuthorDto request = new BookAuthorDto();
        request.setIsbn("123");
        request.setAuthorID(1);
        request.setPrimaryAuthor("Y");

        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(bookAuthorRepository.save(any(BookAuthor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookAuthorDto result = bookAuthorService.create(request);

        assertEquals("123", result.getIsbn());
        assertEquals(1, result.getAuthorID());
        assertEquals("Y", result.getPrimaryAuthor());
    }

    @Test
    void createShouldThrowWhenBookNotFound() {

        BookAuthorDto request = new BookAuthorDto();
        request.setIsbn("123");
        request.setAuthorID(1);

        when(bookRepository.findById("123")).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookAuthorService.create(request));

        assertEquals("Book not found with id: 123", ex.getMessage());
    }

    @Test
    void createShouldThrowWhenAuthorNotFound() {

        Book book = new Book();

        BookAuthorDto request = new BookAuthorDto();
        request.setIsbn("123");
        request.setAuthorID(1);

        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(authorRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookAuthorService.create(request));

        assertEquals("Author not found with id: 1", ex.getMessage());
    }

    @Test
    void updateShouldReturnDto() {

        BookAuthor existing = new BookAuthor();
        existing.setId(new BookAuthorId("123", 1));

        Book book = new Book();
        Author author = new Author();

        BookAuthorDto request = new BookAuthorDto();
        request.setIsbn("123");
        request.setAuthorID(1);
        request.setPrimaryAuthor("N");

        when(bookAuthorRepository.findById(any(BookAuthorId.class)))
                .thenReturn(Optional.of(existing));
        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(bookAuthorRepository.save(any(BookAuthor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        BookAuthorDto result = bookAuthorService.update("123", 1, request);

        assertEquals("123", result.getIsbn());
        assertEquals(1, result.getAuthorID());
        assertEquals("N", result.getPrimaryAuthor());
    }

    @Test
    void updateShouldThrowWhenRelationNotFound() {

        BookAuthorDto request = new BookAuthorDto();

        when(bookAuthorRepository.findById(any(BookAuthorId.class)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookAuthorService.update("123", 1, request));

        assertEquals(
                "BookAuthor not found for isbn 123 and author 1",
                ex.getMessage()
        );
    }

    @Test
    void deleteShouldRemoveEntity() {

        BookAuthor entity = new BookAuthor();
        entity.setId(new BookAuthorId("123", 1));

        when(bookAuthorRepository.findById(any(BookAuthorId.class)))
                .thenReturn(Optional.of(entity));

        bookAuthorService.delete("123", 1);

        verify(bookAuthorRepository).delete(entity);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {

        when(bookAuthorRepository.findById(any(BookAuthorId.class)))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> bookAuthorService.delete("123", 1));

        assertEquals(
                "BookAuthor not found for isbn 123 and author 1",
                ex.getMessage()
        );
    }
}