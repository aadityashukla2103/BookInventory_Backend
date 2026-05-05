package com.cg.service;

import com.cg.dto.BookDto;
import com.cg.entity.Book;
import com.cg.entity.BookCondition;
import com.cg.entity.Category;
import com.cg.entity.Inventory;
import com.cg.entity.Publisher;
import com.cg.exception.BadRequestException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookConditionRepository;
import com.cg.repo.BookRepository;
import com.cg.repo.CategoryRepository;
import com.cg.repo.InventoryRepository;
import com.cg.repo.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;
    private final InventoryRepository inventoryRepository;
    private final BookConditionRepository bookConditionRepository;

    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       PublisherRepository publisherRepository,
                       InventoryRepository inventoryRepository,
                       BookConditionRepository bookConditionRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
        this.inventoryRepository = inventoryRepository;
        this.bookConditionRepository = bookConditionRepository;
    }

    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(this::toDto).toList();
    }

    public BookDto getById(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new BadRequestException("ISBN cannot be null or empty");
        }
        return toDto(findEntity(isbn));
    }

    public BookDto create(BookDto dto) {
        if (dto == null || dto.getIsbn() == null || dto.getIsbn().isBlank()) {
            throw new BadRequestException("Invalid book data");
        }

        Book entity = new Book();
        entity.setIsbn(dto.getIsbn());
        apply(dto, entity);

        Book saved = bookRepository.save(entity);
        createInitialInventory(saved);
        return toDto(saved);
    }

    public BookDto update(String isbn, BookDto dto) {
        if (isbn == null || dto == null) {
            throw new BadRequestException("Invalid input");
        }

        Book entity = findEntity(isbn);
        apply(dto, entity);

        return toDto(bookRepository.save(entity));
    }

    public void delete(String isbn) {
        if (isbn == null) {
            throw new BadRequestException("ISBN cannot be null");
        }
        bookRepository.delete(findEntity(isbn));
    }

    private Book findEntity(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + isbn));
    }

    private void apply(BookDto dto, Book entity) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setEdition(dto.getEdition());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found with id: " + dto.getCategoryId()));
            entity.setCategory(category);
        }

        Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Publisher not found with id: " + dto.getPublisherId()));
        entity.setPublisher(publisher);
    }

    private void createInitialInventory(Book book) {
        BookCondition condition = bookConditionRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book condition is required before creating inventory"));

        Inventory inventory = new Inventory();
        inventory.setBook(book);
        inventory.setBookCondition(condition);
        inventory.setPurchased(false);
        inventoryRepository.save(inventory);
    }

    private BookDto toDto(Book entity) {
        BookDto dto = new BookDto();

        dto.setIsbn(entity.getIsbn());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setEdition(entity.getEdition());

        // IDs (keep these)
        dto.setCategoryId(
            entity.getCategory() != null ? entity.getCategory().getCatId() : null
        );

        dto.setPublisherId(
            entity.getPublisher() != null ? entity.getPublisher().getPublisherId() : null
        );

        // ✅ ADD THESE (IMPORTANT)
        dto.setCategoryName(
            entity.getCategory() != null ? entity.getCategory().getCategoryName() : null
        );

        dto.setPublisherName(
            entity.getPublisher() != null ? entity.getPublisher().getName() : null
        );

        return dto;
    }
}
