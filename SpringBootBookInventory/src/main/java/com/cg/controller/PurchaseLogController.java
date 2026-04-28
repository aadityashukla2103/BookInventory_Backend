package com.cg.controller;

import com.cg.dto.PurchaseLogDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.PurchaseLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-logs")
public class PurchaseLogController {
    private final PurchaseLogService purchaseLogService;

    public PurchaseLogController(PurchaseLogService purchaseLogService) {
    	this.purchaseLogService = purchaseLogService;
    }

    @GetMapping
    public List<PurchaseLogDto> getAll() {
    	return purchaseLogService.getAll();
    }

    @GetMapping("/{userId}/{inventoryId}")
    public PurchaseLogDto getById(@PathVariable Integer userId, @PathVariable Integer inventoryId) {
        return purchaseLogService.getById(userId, inventoryId);
    }

    @PostMapping
    public ResponseEntity<PurchaseLogDto> create(@RequestBody PurchaseLogDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseLogService.create(dto));
    }

    @PutMapping("/{userId}/{inventoryId}")
    public PurchaseLogDto update(@PathVariable Integer userId, @PathVariable Integer inventoryId, @RequestBody PurchaseLogDto dto) {
        return purchaseLogService.update(userId, inventoryId, dto);
    }

    @DeleteMapping("/{userId}/{inventoryId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId, @PathVariable Integer inventoryId) {
        purchaseLogService.delete(userId, inventoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public long count() {
        return purchaseLogService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return purchaseLogService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<PurchaseLogDto> getFirst() {
        return purchaseLogService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
   
    @GetMapping("/last")
    public ResponseEntity<PurchaseLogDto> getLast() {
        List<PurchaseLogDto> items = purchaseLogService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    
    @GetMapping("/exists")
    public boolean exists(@RequestParam Integer userId, @RequestParam Integer inventoryId) {
        try {
            purchaseLogService.getById(userId, inventoryId);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}
