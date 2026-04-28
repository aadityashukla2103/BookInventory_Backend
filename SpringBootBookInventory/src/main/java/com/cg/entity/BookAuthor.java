package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookauthor")
public class BookAuthor {

	@EmbeddedId
	private BookAuthorId id;

	@ManyToOne
	@MapsId("ISBN")
	@JoinColumn(name = "\"ISBN\"")
	private Book book;

	@ManyToOne
	@MapsId("authorID")
	@JoinColumn(name = "\"authorID\"")
	private Author author;

	@Column(name = "\"PrimaryAuthor\"")
	private String primaryAuthor;

	public BookAuthor() {
	}

	public BookAuthorId getId() {
		return id;
	}

	public void setId(BookAuthorId id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getPrimaryAuthor() {
		return primaryAuthor;
	}

	public void setPrimaryAuthor(String primaryAuthor) {
		this.primaryAuthor = primaryAuthor;
	}
}