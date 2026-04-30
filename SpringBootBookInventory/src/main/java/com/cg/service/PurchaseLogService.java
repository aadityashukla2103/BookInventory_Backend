package com.cg.service;

import com.cg.dto.PurchaseLogDto;
import com.cg.entity.Inventory;
import com.cg.entity.PurchaseLog;
import com.cg.entity.PurchaseLogId;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.InventoryRepository;
import com.cg.repo.PurchaseLogRepository;
import com.cg.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseLogService {
    private final PurchaseLogRepository purchaseLogRepository;
    private final UserRepository userRepository;
    private final InventoryRepository inventoryRepository;

    public PurchaseLogService(PurchaseLogRepository purchaseLogRepository,
                              UserRepository userRepository,
                              InventoryRepository inventoryRepository) {
        this.purchaseLogRepository = purchaseLogRepository;
        this.userRepository = userRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public List<PurchaseLogDto> getAll() {
        return purchaseLogRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public PurchaseLogDto getById(Integer userId, Integer inventoryId) {
        if (userId == null || inventoryId == null) {
            throw new IllegalArgumentException("User ID and Inventory ID cannot be null");
        }
        return toDto(findEntity(new PurchaseLogId(userId, inventoryId)));
    }

    public PurchaseLogDto create(PurchaseLogDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("PurchaseLog data cannot be null");
        }

        PurchaseLog entity = new PurchaseLog();
        apply(dto, entity);

        return toDto(purchaseLogRepository.save(entity));
    }

    public PurchaseLogDto update(Integer userId, Integer inventoryId, PurchaseLogDto dto) {
        if (userId == null || inventoryId == null || dto == null) {
            throw new IllegalArgumentException("Invalid input for update");
        }

        PurchaseLog entity = findEntity(new PurchaseLogId(userId, inventoryId));
        apply(dto, entity);

        return toDto(purchaseLogRepository.save(entity));
    }

    public void delete(Integer userId, Integer inventoryId) {
        if (userId == null || inventoryId == null) {
            throw new IllegalArgumentException("User ID and Inventory ID cannot be null");
        }

        purchaseLogRepository.delete(findEntity(new PurchaseLogId(userId, inventoryId)));
    }

    private PurchaseLog findEntity(PurchaseLogId id) {
        return purchaseLogRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PurchaseLog not found for user "
                                        + id.getUserID() + " and inventory " + id.getInventoryID()));
    }

    private void apply(PurchaseLogDto dto, PurchaseLog entity) {
        User user = userRepository.findById(dto.getUserID())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + dto.getUserID()));

        Inventory inventory = inventoryRepository.findById(dto.getInventoryID())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory not found with id: " + dto.getInventoryID()));

        entity.setId(new PurchaseLogId(dto.getUserID(), dto.getInventoryID()));
        entity.setUser(user);
        entity.setInventory(inventory);
    }

    private PurchaseLogDto toDto(PurchaseLog entity) {
        PurchaseLogDto dto = new PurchaseLogDto();
        dto.setUserID(entity.getId() != null ? entity.getId().getUserID() : null);
        dto.setInventoryID(entity.getId() != null ? entity.getId().getInventoryID() : null);
        return dto;
    }
}