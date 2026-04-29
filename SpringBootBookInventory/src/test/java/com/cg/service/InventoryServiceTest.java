package com.cg.service;

import com.cg.dto.InventoryDto;
import com.cg.entity.Book;
import com.cg.entity.BookCondition;
import com.cg.entity.Inventory;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookConditionRepository;
import com.cg.repo.BookRepository;
import com.cg.repo.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookConditionRepository bookConditionRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Book createBook() {
        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setTitle("Java Fundamentals");
        book.setDescription("Core Java concepts");
        book.setEdition("1st");
        return book;
    }

    private BookCondition createCondition() {
        BookCondition condition = new BookCondition();
        condition.setRanks(1);
        return condition;
    }

    private Inventory createInventory() {
        Inventory inventory = new Inventory();
        inventory.setInventoryID(100);
        inventory.setBook(createBook());
        inventory.setBookCondition(createCondition());
        inventory.setPurchased(true);
        return inventory;
    }

    private InventoryDto createDto() {
        InventoryDto dto = new InventoryDto();
        dto.setInventoryID(100);
        dto.setIsbn("1234567890123");
        dto.setRanks(1);
        dto.setPurchased(true);
        return dto;
    }

    @Test
    void testGetAll_success() {
        when(inventoryRepository.findAll()).thenReturn(List.of(createInventory()));

        List<InventoryDto> result = inventoryService.getAll();

        assertEquals(1, result.size());
        assertEquals("1234567890123", result.get(0).getIsbn());

        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    void testGetById_success() {
        when(inventoryRepository.findById(100))
                .thenReturn(Optional.of(createInventory()));

        InventoryDto result = inventoryService.getById(100);

        assertEquals(100, result.getInventoryID());
        assertEquals("1234567890123", result.getIsbn());
    }

    @Test
    void testGetById_notFound() {
        when(inventoryRepository.findById(100))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.getById(100));
    }

    @Test
    void testCreate_success() {
        InventoryDto dto = createDto();
        Book book = createBook();
        BookCondition condition = createCondition();

        when(bookRepository.findById("1234567890123"))
                .thenReturn(Optional.of(book));
        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.of(condition));
        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(createInventory());

        InventoryDto result = inventoryService.create(dto);

        assertNotNull(result);
        assertEquals("1234567890123", result.getIsbn());

        verify(inventoryRepository).save(any(Inventory.class));
    }

    @Test
    void testUpdate_success() {
        InventoryDto dto = createDto();
        Inventory existing = createInventory();

        when(inventoryRepository.findById(100))
                .thenReturn(Optional.of(existing));
        when(bookRepository.findById("1234567890123"))
                .thenReturn(Optional.of(existing.getBook()));
        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.of(existing.getBookCondition()));
        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(existing);

        InventoryDto result = inventoryService.update(100, dto);

        assertEquals(100, result.getInventoryID());
        verify(inventoryRepository).save(existing);
    }

    @Test
    void testDelete_success() {
        Inventory inventory = createInventory();

        when(inventoryRepository.findById(100))
                .thenReturn(Optional.of(inventory));

        inventoryService.delete(100);

        verify(inventoryRepository, times(1)).delete(inventory);
    }

    @Test
    void testCreate_bookNotFound() {
        InventoryDto dto = createDto();

        when(bookRepository.findById("1234567890123"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.create(dto));
    }

    @Test
    void testCreate_conditionNotFound() {
        InventoryDto dto = createDto();
        Book book = createBook();

        when(bookRepository.findById("1234567890123"))
                .thenReturn(Optional.of(book));
        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.create(dto));
    }
}