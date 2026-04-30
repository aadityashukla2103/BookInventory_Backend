package com.cg.dto;

import jakarta.validation.constraints.*;

public class PurchaseLogDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be greater than 0")
    private Integer userID;

    @NotNull(message = "Inventory ID is required")
    @Positive(message = "Inventory ID must be greater than 0")
    private Integer inventoryID;

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
}