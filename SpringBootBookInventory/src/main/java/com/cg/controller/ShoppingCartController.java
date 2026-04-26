package com.cg.controller;

import com.cg.dto.ShoppingCartDto;
import com.cg.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartService service;
    public ShoppingCartController(ShoppingCartService service){ this.service=service; }
    @GetMapping public List<ShoppingCartDto> all(){ return service.getAll(); }
    @PostMapping public ShoppingCartDto create(@RequestBody ShoppingCartDto dto){ return service.save(dto); }
    @DeleteMapping public void delete(@RequestParam Integer userId, @RequestParam String isbn){ service.delete(userId, isbn); }
}
