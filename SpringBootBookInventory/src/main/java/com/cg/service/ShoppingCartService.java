package com.cg.service;

import com.cg.dto.ShoppingCartDto;
import com.cg.entity.Book;
import com.cg.entity.ShoppingCart;
import com.cg.entity.ShoppingCartId;
import com.cg.entity.User;
import com.cg.exception.ResourceNotFoundException;
import com.cg.repo.BookRepository;
import com.cg.repo.ShoppingCartRepository;
import com.cg.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               UserRepository userRepository,
                               BookRepository bookRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<ShoppingCartDto> getAll() {
        return shoppingCartRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ShoppingCartDto getById(Integer userId, String isbn) {
        if (userId == null || isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("User ID and ISBN must not be null or empty");
        }

        return toDto(findEntity(new ShoppingCartId(userId, isbn)));
    }

    public ShoppingCartDto create(ShoppingCartDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ShoppingCart data cannot be null");
        }

        ShoppingCart entity = new ShoppingCart();
        apply(dto, entity);

        return toDto(shoppingCartRepository.save(entity));
    }

    public ShoppingCartDto update(Integer userId, String isbn, ShoppingCartDto dto) {
        if (userId == null || isbn == null || isbn.isBlank() || dto == null) {
            throw new IllegalArgumentException("Invalid input for update");
        }

        ShoppingCart entity = findEntity(new ShoppingCartId(userId, isbn));
        apply(dto, entity);

        return toDto(shoppingCartRepository.save(entity));
    }

    public void delete(Integer userId, String isbn) {
        if (userId == null || isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("User ID and ISBN must not be null or empty");
        }

        shoppingCartRepository.delete(findEntity(new ShoppingCartId(userId, isbn)));
    }

    private ShoppingCart findEntity(ShoppingCartId id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ShoppingCart not found for user "
                                        + id.getUserID() + " and isbn " + id.getISBN()));
    }

    private void apply(ShoppingCartDto dto, ShoppingCart entity) {
        User user = userRepository.findById(dto.getUserID())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + dto.getUserID()));

        Book book = bookRepository.findById(dto.getIsbn())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + dto.getIsbn()));

        entity.setId(new ShoppingCartId(dto.getUserID(), dto.getIsbn()));
        entity.setUser(user);
        entity.setBook(book);
    }

    private ShoppingCartDto toDto(ShoppingCart entity) {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setUserID(entity.getId() != null ? entity.getId().getUserID() : null);
        dto.setIsbn(entity.getId() != null ? entity.getId().getISBN() : null);
        return dto;
    }
}