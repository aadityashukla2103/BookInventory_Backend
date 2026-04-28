package com.cg.service;

import com.cg.dto.InventoryDto;
import com.cg.entity.Book;
import com.cg.entity.BookCondition;
import com.cg.entity.Inventory;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookConditionRepository;
import com.cg.repo.BookRepository;
import com.cg.repo.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final BookRepository bookRepository;
    private final BookConditionRepository bookConditionRepository;

    public InventoryService(InventoryRepository inventoryRepository, BookRepository bookRepository, BookConditionRepository bookConditionRepository) {
        this.inventoryRepository = inventoryRepository;
        this.bookRepository = bookRepository;
        this.bookConditionRepository = bookConditionRepository;
    }

    public List<InventoryDto> getAll() { return inventoryRepository.findAll().stream().map(this::toDto).toList(); }
    public InventoryDto getById(Integer id) { return toDto(findEntity(id)); }

    public InventoryDto create(InventoryDto dto) {
        Inventory entity = new Inventory();
        apply(dto, entity);
        return toDto(inventoryRepository.save(entity));
    }

    public InventoryDto update(Integer id, InventoryDto dto) {
        Inventory entity = findEntity(id);
        apply(dto, entity);
        return toDto(inventoryRepository.save(entity));
    }

    public void delete(Integer id) { inventoryRepository.delete(findEntity(id)); }

    private Inventory findEntity(Integer id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + id));
    }

    private void apply(InventoryDto dto, Inventory entity) {
        Book book = bookRepository.findById(dto.getIsbn())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getIsbn()));
        BookCondition condition = bookConditionRepository.findById(dto.getRanks())
                .orElseThrow(() -> new ResourceNotFoundException("BookCondition not found with id: " + dto.getRanks()));
        entity.setBook(book);
        entity.setBookCondition(condition);
        entity.setPurchased(dto.getPurchased());
    }

    private InventoryDto toDto(Inventory entity) {
        InventoryDto dto = new InventoryDto();
        dto.setInventoryID(entity.getInventoryID());
        dto.setIsbn(entity.getBook() != null ? entity.getBook().getIsbn() : null);
        dto.setRanks(entity.getBookCondition() != null ? entity.getBookCondition().getRanks() : null);
        dto.setPurchased(entity.getPurchased());
        return dto;
    }
}
