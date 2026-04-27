package com.cg.dto;

public class PurchaseLogDto {
	
    private Integer userID;
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
