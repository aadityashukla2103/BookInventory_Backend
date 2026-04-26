package com.cg.service;

import com.cg.dto.ShoppingCartDto;
import com.cg.entity.*;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookRepository;
import com.cg.repo.ShoppingCartRepository;
import com.cg.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository repo;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ShoppingCartService(ShoppingCartRepository repo, UserRepository userRepository, BookRepository bookRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<ShoppingCartDto> getAll(){ return repo.findAll().stream().map(sc -> new ShoppingCartDto(sc.getId().getUserID(), sc.getId().getISBN())).toList(); }
    public ShoppingCartDto save(ShoppingCartDto dto){ ShoppingCart sc = new ShoppingCart(); sc.setId(new ShoppingCartId(dto.userID(), dto.isbn())); sc.setUser(userRepository.findById(dto.userID()).orElseThrow(() -> new ResourceNotFoundException("User not found"))); sc.setBook(bookRepository.findById(dto.isbn()).orElseThrow(() -> new ResourceNotFoundException("Book not found"))); ShoppingCart saved = repo.save(sc); return new ShoppingCartDto(saved.getId().getUserID(), saved.getId().getISBN()); }
    public void delete(Integer userId, String isbn){ repo.deleteById(new ShoppingCartId(userId, isbn)); }
}
