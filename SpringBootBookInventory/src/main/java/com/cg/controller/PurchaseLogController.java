package com.cg.controller;

import com.cg.dto.PurchaseLogDto;
import com.cg.service.PurchaseLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-logs")
public class PurchaseLogController {
    private final PurchaseLogService service;
    public PurchaseLogController(PurchaseLogService service){ this.service=service; }
    @GetMapping public List<PurchaseLogDto> all(){ return service.getAll(); }
    @PostMapping public PurchaseLogDto create(@RequestBody PurchaseLogDto dto){ return service.save(dto); }
    @DeleteMapping public void delete(@RequestParam Integer userId, @RequestParam Integer inventoryId){ service.delete(userId, inventoryId); }
}
