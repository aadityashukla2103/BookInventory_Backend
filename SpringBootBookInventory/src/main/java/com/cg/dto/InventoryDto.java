package com.cg.dto;

public class InventoryDto {
	
    private Integer inventoryID;
    private String isbn;
    private Integer ranks;
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
