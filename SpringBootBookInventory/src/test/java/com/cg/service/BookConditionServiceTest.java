package com.cg.service;

import com.cg.dto.BookConditionDto;
import com.cg.entity.BookCondition;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookConditionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookConditionServiceTest {

    @Mock
    private BookConditionRepository bookConditionRepository;

    @InjectMocks
    private BookConditionService bookConditionService;

    private BookCondition createEntity() {
        BookCondition entity = new BookCondition();
        entity.setRanks(1);
        entity.setDescription("New");
        entity.setFullDescription("Brand New Book");
        entity.setPrice(500.0);
        return entity;
    }

    private BookConditionDto createDto() {
        BookConditionDto dto = new BookConditionDto();
        dto.setRanks(1);
        dto.setDescription("New");
        dto.setFullDescription("Brand New Book");
        dto.setPrice(500.0);
        return dto;
    }

    @Test
    void testGetAll_success() {
        when(bookConditionRepository.findAll()).thenReturn(List.of(createEntity()));

        List<BookConditionDto> result = bookConditionService.getAll();

        assertEquals(1, result.size());
        assertEquals("New", result.get(0).getDescription());

        verify(bookConditionRepository, times(1)).findAll();
    }

    @Test
    void testGetById_success() {
        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.of(createEntity()));

        BookConditionDto result = bookConditionService.getById(1);

        assertEquals(1, result.getRanks());
        assertEquals("New", result.getDescription());
    }

    @Test
    void testGetById_notFound() {
        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookConditionService.getById(1));
    }

    @Test
    void testCreate_success() {
        BookConditionDto dto = createDto();

        when(bookConditionRepository.save(any(BookCondition.class)))
                .thenReturn(createEntity());

        BookConditionDto result = bookConditionService.create(dto);

        assertNotNull(result);
        assertEquals("New", result.getDescription());
        assertEquals(500.0, result.getPrice());

        verify(bookConditionRepository).save(any(BookCondition.class));
    }

    @Test
    void testUpdate_success() {
        BookCondition existing = createEntity();
        BookConditionDto dto = createDto();

        dto.setDescription("Updated");
        dto.setPrice(600.0);

        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.of(existing));
        when(bookConditionRepository.save(any(BookCondition.class)))
                .thenReturn(existing);

        BookConditionDto result = bookConditionService.update(1, dto);

        assertEquals("Updated", result.getDescription());
        assertEquals(600.0, result.getPrice());

        verify(bookConditionRepository).save(existing);
    }

    @Test
    void testUpdate_notFound() {
        BookConditionDto dto = createDto();

        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookConditionService.update(1, dto));
    }

    @Test
    void testDelete_success() {
        BookCondition entity = createEntity();

        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.of(entity));

        bookConditionService.delete(1);

        verify(bookConditionRepository, times(1)).delete(entity);
    }

    @Test
    void testDelete_notFound() {
        when(bookConditionRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookConditionService.delete(1));
    }
}
