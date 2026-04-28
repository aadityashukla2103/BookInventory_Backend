package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {
	@EmbeddedId
	private ShoppingCartId id;

	@ManyToOne
	@MapsId("userID")
	@JoinColumn(name = "\"UserID\"")
	private User user;

	@ManyToOne
	@MapsId("ISBN")
	@JoinColumn(name = "\"ISBN\"")
	private Book book;

	public ShoppingCart() {
	}

	public ShoppingCart(ShoppingCartId id, User user, Book book) {
		this.id = id;
		this.user = user;
		this.book = book;
	}

	public ShoppingCartId getId() {
		return id;
	}

	public void setId(ShoppingCartId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}