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

    private final BookRepository repo;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookService(BookRepository repo, CategoryRepository categoryRepository, PublisherRepository publisherRepository) {
        this.repo = repo;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    public List<BookDto> getAll() { return repo.findAll().stream().map(this::toDto).toList(); }
    public BookDto getById(String isbn) { return toDto(repo.findById(isbn).orElseThrow(() -> new ResourceNotFoundException("Book not found"))); }
    public BookDto save(BookDto dto) { return toDto(repo.save(toEntity(dto))); }
    public void delete(String isbn) { repo.deleteById(isbn); }
    public List<BookDto> searchByTitle(String title) { return repo.findByTitleContainingIgnoreCase(title).stream().map(this::toDto).toList(); }

    private BookDto toDto(Book b){ return new BookDto(b.getIsbn(), b.getTitle(), b.getDescription(), b.getEdition(), b.getCategory()==null?null:b.getCategory().getCatId(), b.getPublisher()==null?null:b.getPublisher().getPublisherId()); }
    private Book toEntity(BookDto d){ Book b=new Book(); b.setIsbn(d.isbn()); b.setTitle(d.title()); b.setDescription(d.description()); b.setEdition(d.edition()); if(d.categoryId()!=null){ Category c=categoryRepository.findById(d.categoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found")); b.setCategory(c);} if(d.publisherId()!=null){ Publisher p=publisherRepository.findById(d.publisherId()).orElseThrow(() -> new ResourceNotFoundException("Publisher not found")); b.setPublisher(p);} return b; }
}
