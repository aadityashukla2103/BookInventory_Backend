package com.cg.dto;

import jakarta.validation.constraints.*;

public class ShoppingCartDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Integer userID;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}