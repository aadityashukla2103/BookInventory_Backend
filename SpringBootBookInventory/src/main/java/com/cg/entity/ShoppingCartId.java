package com.cg.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShoppingCartId implements Serializable {

	@Column(name = "UserID")
	private Integer userId;
	
	@Column(name = "ISBN")
	private String isbn;

    public ShoppingCartId() {
    }

    public ShoppingCartId(Integer userID, String ISBN) {
        this.userId = userID;
        this.isbn = ISBN;
    }

    public Integer getUserID() {
        return userId;
    }

    public void setUserID(Integer userID) {
        this.userId = userID;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartId)) return false;
        ShoppingCartId that = (ShoppingCartId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(isbn, that.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, isbn);
    }
}