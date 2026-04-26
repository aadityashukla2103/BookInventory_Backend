package com.cg.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseLogId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userID;
    private Integer inventoryID;

    public PurchaseLogId() {}

    public PurchaseLogId(Integer userID, Integer inventoryID) {
        this.userID = userID;
        this.inventoryID = inventoryID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseLogId that = (PurchaseLogId) o;
        return Objects.equals(userID, that.userID) &&
               Objects.equals(inventoryID, that.inventoryID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, inventoryID);
    }

    @Override
    public String toString() {
        return "PurchaseLogId{" + "userID=" + userID + ", inventoryID=" + inventoryID + '}';
    }
    
}