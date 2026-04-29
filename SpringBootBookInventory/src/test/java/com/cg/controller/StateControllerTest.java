package com.cg.controller;

import com.cg.dto.StateDto;
import com.cg.service.StateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateControllerTest {

	@Mock
	private StateService stateService;

	@InjectMocks
	private StateController stateController;

	@Test
	void getAllShouldReturnList() {

		StateDto s1 = new StateDto();
		s1.setStateCode("UP");
		s1.setStateName("Uttar Pradesh");

		StateDto s2 = new StateDto();
		s2.setStateCode("DL");
		s2.setStateName("Delhi");

		when(stateService.getAll()).thenReturn(Arrays.asList(s1, s2));

		List<StateDto> result = stateController.getAll();

		assertEquals(2, result.size());
		assertEquals("UP", result.get(0).getStateCode());
		assertEquals("Uttar Pradesh", result.get(0).getStateName());
		assertEquals("DL", result.get(1).getStateCode());

		verify(stateService).getAll();
	}

	@Test
	void getByIdShouldReturnState() {

		StateDto dto = new StateDto();
		dto.setStateCode("UP");
		dto.setStateName("Uttar Pradesh");

		when(stateService.getById("UP")).thenReturn(dto);

		StateDto result = stateController.getById("UP");

		assertEquals("UP", result.getStateCode());
		assertEquals("Uttar Pradesh", result.getStateName());

		verify(stateService).getById("UP");
	}

	@Test
	void createShouldReturnCreatedResponse() {

		StateDto request = new StateDto();
		request.setStateCode("UP");
		request.setStateName("Uttar Pradesh");

		StateDto response = new StateDto();
		response.setStateCode("UP");
		response.setStateName("Uttar Pradesh");

		when(stateService.create(request)).thenReturn(response);

		ResponseEntity<StateDto> result = stateController.create(request);

		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertNotNull(result.getBody());
		assertEquals("UP", result.getBody().getStateCode());
		assertEquals("Uttar Pradesh", result.getBody().getStateName());

		verify(stateService).create(request);
	}

	@Test
	void updateShouldReturnUpdatedState() {

		StateDto request = new StateDto();
		request.setStateName("Updated Name");

		StateDto response = new StateDto();
		response.setStateCode("UP");
		response.setStateName("Updated Name");

		when(stateService.update("UP", request)).thenReturn(response);

		StateDto result = stateController.update("UP", request);

		assertEquals("UP", result.getStateCode());
		assertEquals("Updated Name", result.getStateName());

		verify(stateService).update("UP", request);
	}

	@Test
	void deleteShouldReturnNoContent() {

		doNothing().when(stateService).delete("UP");

		ResponseEntity<Void> result = stateController.delete("UP");

		assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
		assertNull(result.getBody());

		verify(stateService).delete("UP");
	}
}