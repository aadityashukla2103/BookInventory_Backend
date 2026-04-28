package com.cg.service;

import com.cg.dto.BookDto;
import com.cg.entity.Book;
import com.cg.entity.Category;
import com.cg.entity.Publisher;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookRepository;
import com.cg.repo.CategoryRepository;
import com.cg.repo.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<BookDto> getAll() { return bookRepository.findAll().stream().map(this::toDto).toList(); }

    public BookDto getById(String isbn) { return toDto(findEntity(isbn)); }

    public BookDto create(BookDto dto) {
        Book entity = new Book();
        entity.setIsbn(dto.getIsbn());
        apply(dto, entity);
        return toDto(bookRepository.save(entity));
    }

    public BookDto update(String isbn, BookDto dto) {
        Book entity = findEntity(isbn);
        apply(dto, entity);
        return toDto(bookRepository.save(entity));
    }

    public void delete(String isbn) { bookRepository.delete(findEntity(isbn)); }

    private Book findEntity(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + isbn));
    }

    private void apply(BookDto dto, Book entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setEdition(dto.getEdition());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            entity.setCategory(category);
        } else {
            entity.setCategory(null);
        }
        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found with id: " + dto.getPublisherId()));
        entity.setPublisher(publisher);
    }

    private BookDto toDto(Book entity) {
        BookDto dto = new BookDto();
        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setEdition(entity.getEdition());
        dto.setCategoryId(entity.getCategory() != null ? entity.getCategory().getCatId() : null);
        dto.setPublisherId(entity.getPublisher() != null ? entity.getPublisher().getPublisherId() : null);
        return dto;
    }
}