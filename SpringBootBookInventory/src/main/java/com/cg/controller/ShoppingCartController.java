package com.cg.controller;

import com.cg.dto.ShoppingCartDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) { this.shoppingCartService = shoppingCartService; }

    @GetMapping
    public List<ShoppingCartDto> getAll() { return shoppingCartService.getAll(); }

    @GetMapping("/{userId}/{isbn}")
    public ShoppingCartDto getById(@PathVariable Integer userId, @PathVariable String isbn) {
        return shoppingCartService.getById(userId, isbn);
    }

    @PostMapping
    public ResponseEntity<ShoppingCartDto> create(@RequestBody ShoppingCartDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.create(dto));
    }

    @PutMapping("/{userId}/{isbn}")
    public ShoppingCartDto update(@PathVariable Integer userId, @PathVariable String isbn, @RequestBody ShoppingCartDto dto) {
        return shoppingCartService.update(userId, isbn, dto);
    }

    @DeleteMapping("/{userId}/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId, @PathVariable String isbn) {
        shoppingCartService.delete(userId, isbn);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/count")
    public long count() {
        return shoppingCartService.getAll().size();
    }

    @GetMapping("/empty")
    public boolean isEmpty() {
        return shoppingCartService.getAll().isEmpty();
    }

    @GetMapping("/first")
    public ResponseEntity<ShoppingCartDto> getFirst() {
        return shoppingCartService.getAll().stream().findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    

    @GetMapping("/last")
    public ResponseEntity<ShoppingCartDto> getLast() {
        List<ShoppingCartDto> items = shoppingCartService.getAll();
        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(items.get(items.size() - 1));
    }
    @GetMapping("/exists")
    public boolean exists(@RequestParam Integer userId, @RequestParam String isbn) {
        try {
            shoppingCartService.getById(userId, isbn);
            return true;
        } catch (ResourceNotFoundException ex) {
            return false;
        }
    }

}