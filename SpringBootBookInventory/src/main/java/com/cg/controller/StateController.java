package com.cg.controller;

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
	public ResponseEntity<StateDto> create(@RequestBody StateDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(stateService.create(dto));
	}

	@PutMapping("/{stateCode}")
	public StateDto update(@PathVariable String stateCode, @RequestBody StateDto dto) {
		return stateService.update(stateCode, dto);
	}

	@DeleteMapping("/{stateCode}")
	public ResponseEntity<Void> delete(@PathVariable String stateCode) {
		stateService.delete(stateCode);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/count")
	public long count() {
		return stateService.getAll().size();
	}

	@GetMapping("/empty")
	public boolean isEmpty() {
		return stateService.getAll().isEmpty();
	}

	@GetMapping("/first")
	public ResponseEntity<StateDto> getFirst() {
		return stateService.getAll().stream().findFirst().map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}

	@GetMapping("/last")
	public ResponseEntity<StateDto> getLast() {
		List<StateDto> items = stateService.getAll();
		if (items.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(items.get(items.size() - 1));
	}

	@GetMapping("/exists/{stateCode}")
	public boolean exists(@PathVariable String stateCode) {
		try {
			stateService.getById(stateCode);
			return true;
		} catch (ResourceNotFoundException ex) {
			return false;
		}
	}

}