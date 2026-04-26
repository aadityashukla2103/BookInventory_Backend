package com.cg.service;

import com.cg.dto.AuthorDto;
import com.cg.entity.Author;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository repo;

    public AuthorService(AuthorRepository repo) {
        this.repo = repo;
    }

    public List<AuthorDto> getAll() { return repo.findAll().stream().map(this::toDto).toList(); }
    public AuthorDto get(Integer id) { return toDto(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"))); }
    public AuthorDto save(AuthorDto dto) { return toDto(repo.save(toEntity(dto))); }
    public void delete(Integer id) { repo.deleteById(id); }

    private AuthorDto toDto(Author a){ return new AuthorDto(a.getAuthorID(), a.getFirstName(), a.getLastName(), a.getPhoto()); }
    private Author toEntity(AuthorDto d){ Author a=new Author(); a.setAuthorID(d.authorID()); a.setFirstName(d.firstName()); a.setLastName(d.lastName()); a.setPhoto(d.photo()); return a; }
}
