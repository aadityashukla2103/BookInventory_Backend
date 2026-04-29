package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bookreview")
public class BookReview {

	@EmbeddedId
	private BookReviewId id;

	@ManyToOne
	@MapsId("isbn")
	@JoinColumn(name = "ISBN")
	private Book book;

	@ManyToOne
	@MapsId("reviewerId")
	@JoinColumn(name = "ReviewerID")
	private Reviewer reviewer;

	@Column(name = "Rating")
	private Integer rating;

	@Column(name = "Comments")
	private String comments;

	public BookReview() {
	}

	public BookReview(BookReviewId id, Book book, Reviewer reviewer, Integer rating, String comments) {
		this.id = id;
		this.book = book;
		this.reviewer = reviewer;
		this.rating = rating;
		this.comments = comments;
	}

	public BookReviewId getId() {
		return id;
	}

	public void setId(BookReviewId id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public void setReviewer(Reviewer reviewer) {
		this.reviewer = reviewer;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}