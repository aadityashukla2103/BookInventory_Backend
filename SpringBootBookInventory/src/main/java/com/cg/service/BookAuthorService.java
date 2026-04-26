package com.cg.service;

import com.cg.dto.BookAuthorDto;
import com.cg.entity.*;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.AuthorRepository;
import com.cg.repo.BookAuthorRepository;
import com.cg.repo.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {
    private final BookAuthorRepository repo;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookAuthorService(BookAuthorRepository repo, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.repo = repo;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<BookAuthorDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public BookAuthorDto save(BookAuthorDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(String isbn, Integer authorId){ repo.deleteById(new BookAuthorId(isbn, authorId)); }

    private BookAuthorDto toDto(BookAuthor ba){ return new BookAuthorDto(ba.getId().getISBN(), ba.getId().getAuthorID(), ba.getPrimaryAuthor()); }
    private BookAuthor toEntity(BookAuthorDto d){ BookAuthor ba=new BookAuthor(); ba.setId(new BookAuthorId(d.isbn(), d.authorID())); ba.setPrimaryAuthor(d.primaryAuthor()); Book b=bookRepository.findById(d.isbn()).orElseThrow(() -> new ResourceNotFoundException("Book not found")); Author a=authorRepository.findById(d.authorID()).orElseThrow(() -> new ResourceNotFoundException("Author not found")); ba.setBook(b); ba.setAuthor(a); return ba; }
}
