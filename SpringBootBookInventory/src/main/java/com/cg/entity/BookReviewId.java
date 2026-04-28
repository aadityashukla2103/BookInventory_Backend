package com.cg.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class BookReviewId implements Serializable {

	@Column(name = "\"ISBN\"")
	private String isbn;

	@Column(name = "\"ReviewerID\"")
	private Integer reviewerId;

	public BookReviewId() {
	}

	public BookReviewId(String isbn, Integer reviewerId) {
		this.isbn = isbn;
		this.reviewerId = reviewerId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(Integer reviewerId) {
		this.reviewerId = reviewerId;
	}

	// IMPORTANT: equals & hashCode for composite key

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BookReviewId that = (BookReviewId) o;

		if (!isbn.equals(that.isbn))
			return false;
		return reviewerId.equals(that.reviewerId);
	}

	@Override
	public int hashCode() {
		int result = isbn.hashCode();
		result = 31 * result + reviewerId.hashCode();
		return result;
	}
}