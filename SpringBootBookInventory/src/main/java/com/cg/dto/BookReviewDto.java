package com.cg.dto;

import jakarta.validation.constraints.*;

public class BookReviewDto {

    @NotBlank(message = "ISBN must not be empty")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    // optional advanced check:
    // @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "Invalid ISBN format")
    private String isbn;

    @NotNull(message = "Reviewer ID is required")
    @Positive(message = "Reviewer ID must be greater than 0")
    private Integer reviewerId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(max = 255, message = "Comments cannot exceed 255 characters")
    private String comments;

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