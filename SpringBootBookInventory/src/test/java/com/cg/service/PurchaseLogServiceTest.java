package com.cg.service;

import com.cg.dto.PurchaseLogDto;
import com.cg.entity.*;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.InventoryRepository;
import com.cg.repo.PurchaseLogRepository;
import com.cg.repo.UserRepository;
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
class PurchaseLogServiceTest {

    @Mock
    private PurchaseLogRepository purchaseLogRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private PurchaseLogService purchaseLogService;

    private User createUser() {
        User user = new User();
        user.setUserID(1);
        return user;
    }

    private Inventory createInventory() {
        Inventory inventory = new Inventory();
        inventory.setInventoryID(100);
        return inventory;
    }

    private PurchaseLog createEntity() {
        User user = createUser();
        Inventory inventory = createInventory();

        PurchaseLog entity = new PurchaseLog();
        entity.setUser(user);
        entity.setInventory(inventory);

        PurchaseLogId id = new PurchaseLogId(user.getUserID(), inventory.getInventoryID());
        entity.setId(id);

        return entity;
    }

    private PurchaseLogDto createDto() {
        PurchaseLogDto dto = new PurchaseLogDto();
        dto.setUserID(1);
        dto.setInventoryID(100);
        return dto;
    }

    @Test
    void testGetAll_success() {
        when(purchaseLogRepository.findAll()).thenReturn(List.of(createEntity()));

        List<PurchaseLogDto> result = purchaseLogService.getAll();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserID());
        assertEquals(100, result.get(0).getInventoryID());

        verify(purchaseLogRepository).findAll();
    }

    @Test
    void testGetById_success() {
        PurchaseLogId id = new PurchaseLogId(1, 100);

        when(purchaseLogRepository.findById(id))
                .thenReturn(Optional.of(createEntity()));

        PurchaseLogDto result = purchaseLogService.getById(1, 100);

        assertEquals(1, result.getUserID());
        assertEquals(100, result.getInventoryID());
    }

    @Test
    void testGetById_notFound() {
        PurchaseLogId id = new PurchaseLogId(1, 100);

        when(purchaseLogRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseLogService.getById(1, 100));
    }

    @Test
    void testCreate_success() {
        PurchaseLogDto dto = createDto();
        User user = createUser();
        Inventory inventory = createInventory();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(inventoryRepository.findById(100)).thenReturn(Optional.of(inventory));
        when(purchaseLogRepository.save(any(PurchaseLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // return same entity

        PurchaseLogDto result = purchaseLogService.create(dto);

        assertNotNull(result);
        assertEquals(1, result.getUserID());
        assertEquals(100, result.getInventoryID());

        verify(purchaseLogRepository).save(any(PurchaseLog.class));
    }

    @Test
    void testUpdate_success() {
        PurchaseLog existing = createEntity();
        PurchaseLogDto dto = createDto();

        when(purchaseLogRepository.findById(new PurchaseLogId(1, 100)))
                .thenReturn(Optional.of(existing));
        when(userRepository.findById(1)).thenReturn(Optional.of(existing.getUser()));
        when(inventoryRepository.findById(100)).thenReturn(Optional.of(existing.getInventory()));
        when(purchaseLogRepository.save(any(PurchaseLog.class)))
                .thenReturn(existing);

        PurchaseLogDto result = purchaseLogService.update(1, 100, dto);

        assertEquals(1, result.getUserID());
        assertEquals(100, result.getInventoryID());

        verify(purchaseLogRepository).save(existing);
    }

    @Test
    void testDelete_success() {
        PurchaseLog entity = createEntity();

        when(purchaseLogRepository.findById(new PurchaseLogId(1, 100)))
                .thenReturn(Optional.of(entity));

        purchaseLogService.delete(1, 100);

        verify(purchaseLogRepository).delete(entity);
    }

    @Test
    void testCreate_userNotFound() {
        PurchaseLogDto dto = createDto();

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseLogService.create(dto));
    }

    @Test
    void testCreate_inventoryNotFound() {
        PurchaseLogDto dto = createDto();
        User user = createUser();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(inventoryRepository.findById(100)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseLogService.create(dto));
    }
    
}
