package com.cg.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class BookAuthorId implements Serializable {

	@Column(name = "ISBN")
	private String isbn;
	
	@Column(name = "authorID")
	private Integer authorId;

	public BookAuthorId() {
	}

	public BookAuthorId(String ISBN, Integer authorID) {
		this.isbn = ISBN;
		this.authorId = authorID;
	}

	public String getISBN() {
		return isbn;
	}

	public void setISBN(String ISBN) {
		this.isbn = ISBN;
	}

	public Integer getAuthorID() {
		return authorId;
	}

	public void setAuthorID(Integer authorID) {
		this.authorId = authorID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BookAuthorId))
			return false;
		BookAuthorId that = (BookAuthorId) o;
		return Objects.equals(isbn, that.isbn) && Objects.equals(authorId, that.authorId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn, authorId);
	}
}