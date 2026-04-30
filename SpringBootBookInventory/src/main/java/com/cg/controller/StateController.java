package com.cg.controller;
import jakarta.validation.Valid;

import com.cg.dto.StateDto;
import com.cg.exception.ResourceNotFoundException;
import com.cg.service.StateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

	private final StateService stateService;

	public StateController(StateService stateService) {
		this.stateService = stateService;
	}

	@GetMapping
	public List<StateDto> getAll() {
		return stateService.getAll();
	}

	@GetMapping("/{stateCode}")
	public StateDto getById(@PathVariable String stateCode) {
		return stateService.getById(stateCode);
	}

	@PostMapping
	public ResponseEntity<StateDto> create(@Valid @RequestBody StateDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stateService.create(dto));
	}

	@PutMapping("/{stateCode}")
	public StateDto update(@PathVariable String stateCode,@Valid @RequestBody StateDto dto) {
		return stateService.update(stateCode, dto);
	}

	@DeleteMapping("/{stateCode}")
	public ResponseEntity<Void> delete(@PathVariable String stateCode) {
		stateService.delete(stateCode);
		return ResponseEntity.noContent().build();
	}

}