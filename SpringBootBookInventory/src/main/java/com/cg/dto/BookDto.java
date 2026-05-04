package com.cg.dto;

import jakarta.validation.constraints.*;

public class BookDto {

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    @NotBlank(message = "Title is required")
    @Size(max = 70, message = "Title must be less than 70 characters")
    private String title;

    @Size(max = 100, message = "Description must be less than 100 characters")
    private String description;

    @Size(max = 30, message = "Edition must be less than 30 characters")
    private String edition;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be greater than 0")
    private Integer categoryId;

    @NotNull(message = "Publisher ID is required")
    @Positive(message = "Publisher ID must be greater than 0")
    private Integer publisherId;
    
    private String publisherName;
    private String categoryName;

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public String getIsbn() { return isbn; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getEdition() { return edition; }

    public void setEdition(String edition) { this.edition = edition; }

    public Integer getCategoryId() { return categoryId; }

    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getPublisherId() { return publisherId; }

    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }
}