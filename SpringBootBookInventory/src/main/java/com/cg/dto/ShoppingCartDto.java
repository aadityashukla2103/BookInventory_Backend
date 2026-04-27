package com.cg.dto;

public class ShoppingCartDto {
    private Integer userID;
    private String isbn;

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}