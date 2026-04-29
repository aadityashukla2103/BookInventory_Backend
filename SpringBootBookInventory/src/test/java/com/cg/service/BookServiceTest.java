package com.cg.service;

import com.cg.dto.BookDto;
import com.cg.entity.Book;
import com.cg.entity.Publisher;
import com.cg.repo.BookRepository;
import com.cg.repo.CategoryRepository;
import com.cg.repo.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    @Test
    void testGetById() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        CategoryRepository catRepo = Mockito.mock(CategoryRepository.class);
        PublisherRepository pubRepo = Mockito.mock(PublisherRepository.class);

        Book book = new Book();
        book.setIsbn("101");

        Mockito.when(repo.findById("101")).thenReturn(Optional.of(book));

        BookService service = new BookService(repo, catRepo, pubRepo);

        BookDto result = service.getById("101");

        assertEquals("101", result.getIsbn());
    }

    @Test
    void testDelete() {
        BookRepository repo = Mockito.mock(BookRepository.class);
        CategoryRepository catRepo = Mockito.mock(CategoryRepository.class);
        PublisherRepository pubRepo = Mockito.mock(PublisherRepository.class);

        Book book = new Book();
        book.setIsbn("101");

        Mockito.when(repo.findById("101")).thenReturn(Optional.of(book));

        BookService service = new BookService(repo, catRepo, pubRepo);

        service.delete("101");

        Mockito.verify(repo).delete(book);
    }
}