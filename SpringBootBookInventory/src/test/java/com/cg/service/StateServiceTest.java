package com.cg.service;

import com.cg.dto.StateDto;
import com.cg.entity.State;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.StateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateServiceTest {

    @Mock
    private StateRepository stateRepository;

    @InjectMocks
    private StateService stateService;

    @Test
    void getAllShouldReturnList() {

        State s1 = new State();
        s1.setStateCode("UP");
        s1.setStateName("Uttar Pradesh");

        State s2 = new State();
        s2.setStateCode("DL");
        s2.setStateName("Delhi");

        when(stateRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<StateDto> result = stateService.getAll();

        assertEquals(2, result.size());
        assertEquals("UP", result.get(0).getStateCode());
        assertEquals("Uttar Pradesh", result.get(0).getStateName());
        assertEquals("DL", result.get(1).getStateCode());

        verify(stateRepository).findAll();
    }

    @Test
    void getByIdShouldReturnState() {

        State state = new State();
        state.setStateCode("UP");
        state.setStateName("Uttar Pradesh");

        when(stateRepository.findById("UP")).thenReturn(Optional.of(state));

        StateDto result = stateService.getById("UP");

        assertEquals("UP", result.getStateCode());
        assertEquals("Uttar Pradesh", result.getStateName());

        verify(stateRepository).findById("UP");
    }

    @Test
    void getByIdShouldThrowWhenNotFound() {

        when(stateRepository.findById("XX")).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> stateService.getById("XX"));

        assertEquals("State not found with id: XX", ex.getMessage());
    }

    @Test
    void createShouldReturnDto() {

        StateDto request = new StateDto();
        request.setStateCode("UP");
        request.setStateName("Uttar Pradesh");

        when(stateRepository.save(any(State.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StateDto result = stateService.create(request);

        assertEquals("UP", result.getStateCode());
        assertEquals("Uttar Pradesh", result.getStateName());

        verify(stateRepository).save(any(State.class));
    }

    @Test
    void updateShouldReturnDto() {

        State existing = new State();
        existing.setStateCode("UP");
        existing.setStateName("Old Name");

        StateDto request = new StateDto();
        request.setStateName("Uttar Pradesh");

        when(stateRepository.findById("UP")).thenReturn(Optional.of(existing));
        when(stateRepository.save(any(State.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StateDto result = stateService.update("UP", request);

        assertEquals("UP", result.getStateCode());
        assertEquals("Uttar Pradesh", result.getStateName());

        verify(stateRepository).findById("UP");
        verify(stateRepository).save(existing);
    }

    @Test
    void updateShouldThrowWhenNotFound() {

        StateDto request = new StateDto();
        request.setStateName("Test");

        when(stateRepository.findById("XX")).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> stateService.update("XX", request));

        assertEquals("State not found with id: XX", ex.getMessage());

        verify(stateRepository, never()).save(any());
    }

    @Test
    void deleteShouldRemoveEntity() {

        State state = new State();
        state.setStateCode("UP");
        state.setStateName("Uttar Pradesh");

        when(stateRepository.findById("UP")).thenReturn(Optional.of(state));

        stateService.delete("UP");

        verify(stateRepository).findById("UP");
        verify(stateRepository).delete(state);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {

        when(stateRepository.findById("XX")).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class,
                        () -> stateService.delete("XX"));

        assertEquals("State not found with id: XX", ex.getMessage());

        verify(stateRepository, never()).delete(any());
    }
}