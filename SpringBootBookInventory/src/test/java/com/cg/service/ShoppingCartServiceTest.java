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
class ShoppingCartServiceTest {

	@Mock
	private ShoppingCartRepository shoppingCartRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	@InjectMocks
	private ShoppingCartService shoppingCartService;

	private ShoppingCart cart;
	private ShoppingCartDto dto;
	private User user;
	private Book book;
	private ShoppingCartId id;

	@BeforeEach
	void setUp() {

		id = new ShoppingCartId(1, "12345");

		user = new User();
		user.setUserID(1);

		book = new Book();
		book.setIsbn("12345");

		cart = new ShoppingCart();
		cart.setId(id);
		cart.setUser(user);
		cart.setBook(book);

		dto = new ShoppingCartDto();
		dto.setUserID(1);
		dto.setIsbn("12345");
	}

	@Test
	void testGetAll() {
		when(shoppingCartRepository.findAll()).thenReturn(Arrays.asList(cart));

		List<ShoppingCartDto> result = shoppingCartService.getAll();

		assertEquals(1, result.size());
		assertEquals("12345", result.get(0).getIsbn());
	}

	@Test
	void testGetById() {
		when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(cart));

		ShoppingCartDto result = shoppingCartService.getById(1, "12345");

		assertEquals(1, result.getUserID());
		assertEquals("12345", result.getIsbn());
	}

	@Test
	void testGetById_NotFound() {
		when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.getById(1, "12345"));
	}

	@Test
	void testCreate() {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(bookRepository.findById("12345")).thenReturn(Optional.of(book));
		when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

		ShoppingCartDto result = shoppingCartService.create(dto);

		assertEquals("12345", result.getIsbn());
		verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
	}

	@Test
	void testUpdate() {
		when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(cart));
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(bookRepository.findById("12345")).thenReturn(Optional.of(book));
		when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

		ShoppingCartDto result = shoppingCartService.update(1, "12345", dto);

		assertEquals(1, result.getUserID());
	}

	@Test
	void testDelete() {
		when(shoppingCartRepository.findById(id)).thenReturn(Optional.of(cart));

		shoppingCartService.delete(1, "12345");

		verify(shoppingCartRepository, times(1)).delete(cart);
	}

	@Test
	void testCreate_UserNotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.create(dto));
	}

	@Test
	void testCreate_BookNotFound() {
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(bookRepository.findById("12345")).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.create(dto));
	}

	@Test
	void testUpdate_NotFound() {
		when(shoppingCartRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> shoppingCartService.update(1, "12345", dto));
	}
}
