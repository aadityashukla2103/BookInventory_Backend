package com.cg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Book;
import com.cg.repo.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;

    public List<Book> getAll() {
        return repo.findAll();
    }

    public Book getById(String isbn) {
        return repo.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book save(Book book) {
        if (book.getIsbn() == null) {
            throw new BadRequestException("ISBN required");
        }
        return repo.save(book);
    }

    public void delete(String isbn) {
        if (!repo.existsById(isbn)) {
            throw new ResourceNotFoundException("Book not found");
        }
        repo.deleteById(isbn);
    }

    public List<Book> searchByTitle(String title) {
        return repo.findByTitleContaining(title);
    }
}