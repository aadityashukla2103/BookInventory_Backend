package com.cg.controller;
import jakarta.validation.Valid;

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
    public ResponseEntity<ShoppingCartDto> create(@Valid @RequestBody ShoppingCartDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.create(dto));
    }

    @PutMapping("/{userId}/{isbn}")
    public ShoppingCartDto update(@PathVariable Integer userId, @PathVariable String isbn,@Valid @RequestBody ShoppingCartDto dto) {
        return shoppingCartService.update(userId, isbn, dto);
    }

    @DeleteMapping("/{userId}/{isbn}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId, @PathVariable String isbn) {
        shoppingCartService.delete(userId, isbn);
        return ResponseEntity.noContent().build();
    }
}