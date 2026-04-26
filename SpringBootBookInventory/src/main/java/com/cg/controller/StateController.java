package com.cg.controller;

import com.cg.dto.StateDto;
import com.cg.service.StateService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {
    private final StateService service;
    public StateController(StateService service){ this.service=service; }
    @GetMapping public List<StateDto> all(){ return service.getAll(); }
    @GetMapping("/{code}") public StateDto one(@PathVariable String code){ return service.get(code); }
    @PostMapping public StateDto create(@RequestBody StateDto dto){ return service.save(dto); }
    @PutMapping("/{code}") public StateDto update(@PathVariable String code,@RequestBody StateDto dto){ return service.save(new StateDto(code,dto.stateName())); }
    @DeleteMapping("/{code}") public void delete(@PathVariable String code){ service.delete(code); }
}
