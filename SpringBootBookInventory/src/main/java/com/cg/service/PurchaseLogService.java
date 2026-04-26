package com.cg.service;

import com.cg.dto.PurchaseLogDto;
import com.cg.entity.*;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.InventoryRepository;
import com.cg.repo.PurchaseLogRepository;
import com.cg.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseLogService {
    private final PurchaseLogRepository repo;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;

    public PurchaseLogService(PurchaseLogRepository repo, UserRepository userRepository, InventoryRepository inventoryRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<PurchaseLogDto> getAll(){ return repo.findAll().stream().map(pl -> new PurchaseLogDto(pl.getId().getUserID(), pl.getId().getInventoryID())).toList(); }
    public PurchaseLogDto save(PurchaseLogDto dto){ PurchaseLog pl = new PurchaseLog(); pl.setId(new PurchaseLogId(dto.userID(), dto.inventoryID())); pl.setUser(userRepository.findById(dto.userID()).orElseThrow(() -> new ResourceNotFoundException("User not found"))); pl.setInventory(inventoryRepository.findById(dto.inventoryID()).orElseThrow(() -> new ResourceNotFoundException("Inventory not found"))); PurchaseLog saved = repo.save(pl); return new PurchaseLogDto(saved.getId().getUserID(), saved.getId().getInventoryID()); }
    public void delete(Integer userId, Integer inventoryId){ repo.deleteById(new PurchaseLogId(userId, inventoryId)); }
}
