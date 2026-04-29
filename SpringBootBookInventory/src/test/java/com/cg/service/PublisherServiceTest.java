package com.cg.service;

import com.cg.dto.PublisherDto;
import com.cg.entity.Publisher;
import com.cg.repo.PublisherRepository;
import com.cg.repo.StateRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PublisherServiceTest {

    @Test
    void testGetById() {
        PublisherRepository repo = Mockito.mock(PublisherRepository.class);
        StateRepository stateRepo = Mockito.mock(StateRepository.class);

        Publisher p = new Publisher();
        p.setPublisherId(1);
        p.setName("ABC");

        Mockito.when(repo.findById(1)).thenReturn(Optional.of(p));

        PublisherService service = new PublisherService(repo, stateRepo);

        PublisherDto dto = service.getById(1);

        assertEquals("ABC", dto.getName());
    }
}