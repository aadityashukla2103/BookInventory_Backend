package com.cg.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ShoppingCartId implements Serializable {

    private Integer userID;
    private String ISBN;

    public ShoppingCartId() {
    }

    public ShoppingCartId(Integer userID, String ISBN) {
        this.userID = userID;
        this.ISBN = ISBN;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCartId)) return false;
        ShoppingCartId that = (ShoppingCartId) o;
        return Objects.equals(userID, that.userID) &&
               Objects.equals(ISBN, that.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, ISBN);
    }
}