```java
package com.cg.service;

import com.cg.dto.PermRoleDto;
import com.cg.entity.PermRole;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.PermRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermRoleServiceTest {

    @Mock
    private PermRoleRepository permRoleRepository;

    @InjectMocks
    private PermRoleService permRoleService;

    private PermRole role;
    private PermRoleDto dto;

    @BeforeEach
    void setUp() {

        role = new PermRole();
        role.setRoleNumber(1);
        role.setPermRole("ADMIN");

        dto = new PermRoleDto();
        dto.setRoleNumber(1);
        dto.setPermRole("ADMIN");
    }

    @Test
    void testGetAll() {
        when(permRoleRepository.findAll()).thenReturn(Arrays.asList(role));

        List<PermRoleDto> result = permRoleService.getAll();

        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getPermRole());
    }

    @Test
    void testGetById() {
        when(permRoleRepository.findById(1)).thenReturn(Optional.of(role));

        PermRoleDto result = permRoleService.getById(1);

        assertEquals(1, result.getRoleNumber());
        assertEquals("ADMIN", result.getPermRole());
    }

    @Test
    void testGetById_NotFound() {
        when(permRoleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> permRoleService.getById(1));
    }

    @Test
    void testCreate() {
        when(permRoleRepository.save(any(PermRole.class))).thenReturn(role);

        PermRoleDto result = permRoleService.create(dto);

        assertEquals("ADMIN", result.getPermRole());
        verify(permRoleRepository, times(1)).save(any(PermRole.class));
    }

    @Test
    void testUpdate() {
        when(permRoleRepository.findById(1)).thenReturn(Optional.of(role));
        when(permRoleRepository.save(any(PermRole.class))).thenReturn(role);

        PermRoleDto result = permRoleService.update(1, dto);

        assertEquals("ADMIN", result.getPermRole());
    }

    @Test
    void testUpdate_NotFound() {
        when(permRoleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> permRoleService.update(1, dto));
    }

    @Test
    void testDelete() {
        when(permRoleRepository.findById(1)).thenReturn(Optional.of(role));

        permRoleService.delete(1);

        verify(permRoleRepository, times(1)).delete(role);
    }

    @Test
    void testDelete_NotFound() {
        when(permRoleRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> permRoleService.delete(1));
    }
}
```
