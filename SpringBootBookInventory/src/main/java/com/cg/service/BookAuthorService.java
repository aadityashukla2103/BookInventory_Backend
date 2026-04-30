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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository,
                             BookRepository bookRepository,
                             AuthorRepository authorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookAuthorDto> getAll() {
        return bookAuthorRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public BookAuthorDto getById(String isbn, Integer authorId) {

        if (isbn == null || authorId == null) {
            throw new IllegalArgumentException("ISBN and Author ID must not be null");
        }

        return toDto(findEntity(new BookAuthorId(isbn, authorId)));
    }

    public BookAuthorDto create(BookAuthorDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        BookAuthor entity = new BookAuthor();
        apply(dto, entity);

        return toDto(bookAuthorRepository.save(entity));
    }

    public BookAuthorDto update(String isbn, Integer authorId, BookAuthorDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        BookAuthor entity = findEntity(new BookAuthorId(isbn, authorId));
        apply(dto, entity);

        return toDto(bookAuthorRepository.save(entity));
    }

    public void delete(String isbn, Integer authorId) {

        if (isbn == null || authorId == null) {
            throw new IllegalArgumentException("ISBN and Author ID must not be null");
        }

        bookAuthorRepository.delete(findEntity(new BookAuthorId(isbn, authorId)));
    }

    private BookAuthor findEntity(BookAuthorId id) {
        return bookAuthorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "BookAuthor not found for ISBN: " + id.getISBN() +
                        " and Author ID: " + id.getAuthorID()
                ));
    }

    private void apply(BookAuthorDto dto, BookAuthor entity) {

        if (dto.getIsbn() == null || dto.getAuthorID() == null) {
            throw new IllegalArgumentException("ISBN and Author ID must not be null");
        }

        Book book = bookRepository.findById(dto.getIsbn())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + dto.getIsbn()));

        Author author = authorRepository.findById(dto.getAuthorID())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found with id: " + dto.getAuthorID()));

        entity.setId(new BookAuthorId(dto.getIsbn(), dto.getAuthorID()));
        entity.setBook(book);
        entity.setAuthor(author);
        entity.setPrimaryAuthor(dto.getPrimaryAuthor());
    }

    private BookAuthorDto toDto(BookAuthor entity) {
        BookAuthorDto dto = new BookAuthorDto();

        dto.setIsbn(entity.getId() != null ? entity.getId().getISBN() : null);
        dto.setAuthorID(entity.getId() != null ? entity.getId().getAuthorID() : null);
        dto.setPrimaryAuthor(entity.getPrimaryAuthor());

        return dto;
    }
}