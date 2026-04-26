package com.cg.controller;

import com.cg.dto.InventoryDto;
import com.cg.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService service;
    public InventoryController(InventoryService service){ this.service=service; }
    @GetMapping public List<InventoryDto> all(){ return service.getAll(); }
    @GetMapping("/{id}") public InventoryDto one(@PathVariable Integer id){ return service.get(id); }
    @PostMapping public InventoryDto create(@RequestBody InventoryDto dto){ return service.save(dto); }
    @PutMapping("/{id}") public InventoryDto update(@PathVariable Integer id,@RequestBody InventoryDto dto){ return service.save(new InventoryDto(id,dto.isbn(),dto.ranks(),dto.purchased())); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){ service.delete(id); }
}
