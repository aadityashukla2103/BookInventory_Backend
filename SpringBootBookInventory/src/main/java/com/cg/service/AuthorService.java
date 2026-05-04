package com.cg.service;

import com.cg.dto.AuthorDto;
import com.cg.entity.Author;
import com.cg.exception.BadRequestException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream().map(this::toDto).toList();
    }

    public AuthorDto getById(Integer authorId) {
        if (authorId == null) {
            throw new BadRequestException("Author ID cannot be null");
        }
        return toDto(findEntity(authorId));
    }

    public AuthorDto create(AuthorDto dto) {
        if (dto == null) {
            throw new BadRequestException("Author data cannot be null");
        }

        Author entity = new Author();
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setPhoto(dto.getPhoto());

        return toDto(authorRepository.save(entity));
    }

    public AuthorDto update(Integer authorId, AuthorDto dto) {
        if (authorId == null || dto == null) {
            throw new BadRequestException("Invalid input");
        }

        Author entity = findEntity(authorId);
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setPhoto(dto.getPhoto());

        return toDto(authorRepository.save(entity));
    }

    public void delete(Integer authorId) {
        if (authorId == null) {
            throw new BadRequestException("Author ID cannot be null");
        }
        authorRepository.delete(findEntity(authorId));
    }

    private Author findEntity(Integer authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + authorId));
    }

    private AuthorDto toDto(Author entity) {
        AuthorDto dto = new AuthorDto();
        dto.setAuthorID(entity.getAuthorID());
        dto.setLastName(entity.getLastName());
        dto.setFirstName(entity.getFirstName());
        dto.setPhoto(entity.getPhoto());
        return dto;
    }
}
