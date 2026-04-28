package com.cg.controller;

import com.cg.dto.InventoryDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
    	this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryDto> getAll() {
    	return inventoryService.getAll();
    }

    @GetMapping("/{inventoryId}")
    public InventoryDto getById(@PathVariable Integer inventoryId) {
    	return inventoryService.getById(inventoryId);
    }

    @PostMapping
    public ResponseEntity<InventoryDto> create(@RequestBody InventoryDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.create(dto));
    }

    @PutMapping("/{inventoryId}")
    public InventoryDto update(@PathVariable Integer inventoryId, @RequestBody InventoryDto dto) {
        return inventoryService.update(inventoryId, dto);
    }

    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> delete(@PathVariable Integer inventoryId) {
        inventoryService.delete(inventoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long count() {
        return inventoryService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return inventoryService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<InventoryDto> getFirst() {
        return inventoryService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/last")
    public ResponseEntity<InventoryDto> getLast() {
        List<InventoryDto> items = inventoryService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    
    @GetMapping("/exists/{inventoryId}")
    public boolean exists(@PathVariable Integer inventoryId) {
        try {
            inventoryService.getById(inventoryId);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}
