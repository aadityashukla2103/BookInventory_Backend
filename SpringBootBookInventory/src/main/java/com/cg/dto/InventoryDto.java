package com.cg.dto;

import jakarta.validation.constraints.*;

public class InventoryDto {

    // ❌ Do NOT validate this for create (DB generates it)
    private Integer inventoryID;

    @NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    private String isbn;

    @NotNull(message = "Rank is required")
    @Positive(message = "Rank must be greater than 0")
    private Integer ranks;

    @NotNull(message = "Purchased flag is required")
    private Boolean purchased;

    public Integer getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(Integer inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getRanks() {
        return ranks;
    }

    public void setRanks(Integer ranks) {
        this.ranks = ranks;
    }

    public Boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }
}