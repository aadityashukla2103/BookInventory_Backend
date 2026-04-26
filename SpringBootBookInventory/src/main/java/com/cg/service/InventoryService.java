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
    private final InventoryRepository repo;
    private final BookRepository bookRepository;
    private final BookConditionRepository conditionRepository;

    public InventoryService(InventoryRepository repo, BookRepository bookRepository, BookConditionRepository conditionRepository) {
        this.repo = repo;
        this.bookRepository = bookRepository;
        this.conditionRepository = conditionRepository;
    }

    public List<InventoryDto> getAll(){ return repo.findAll().stream().map(this::toDto).toList(); }
    public InventoryDto get(Integer id){ return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Inventory not found"))); }
    public InventoryDto save(InventoryDto dto){ return toDto(repo.save(toEntity(dto))); }
    public void delete(Integer id){ repo.deleteById(id); }

    private InventoryDto toDto(Inventory i){ return new InventoryDto(i.getInventoryID(), i.getBook()==null?null:i.getBook().getIsbn(), i.getBookCondition()==null?null:i.getBookCondition().getRanks(), i.getPurchased()); }
    private Inventory toEntity(InventoryDto d){ Inventory i = new Inventory(); i.setInventoryID(d.inventoryID()); i.setPurchased(d.purchased()); if(d.isbn()!=null){ Book b=bookRepository.findById(d.isbn()).orElseThrow(() -> new ResourceNotFoundException("Book not found")); i.setBook(b);} if(d.ranks()!=null){ BookCondition c=conditionRepository.findById(d.ranks()).orElseThrow(() -> new ResourceNotFoundException("Condition not found")); i.setBookCondition(c);} return i; }
}
