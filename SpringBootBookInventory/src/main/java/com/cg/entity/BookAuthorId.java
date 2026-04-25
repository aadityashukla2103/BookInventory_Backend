package com.cg.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class BookAuthorId implements Serializable {

	private String ISBN;
	private Integer authorID;

	public BookAuthorId() {
	}

	public BookAuthorId(String ISBN, Integer authorID) {
		this.ISBN = ISBN;
		this.authorID = authorID;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public Integer getAuthorID() {
		return authorID;
	}

	public void setAuthorID(Integer authorID) {
		this.authorID = authorID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BookAuthorId))
			return false;
		BookAuthorId that = (BookAuthorId) o;
		return Objects.equals(ISBN, that.ISBN) && Objects.equals(authorID, that.authorID);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ISBN, authorID);
	}
}