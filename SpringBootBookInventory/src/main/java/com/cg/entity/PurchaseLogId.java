package com.cg.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseLogId implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "UserID")
	private Integer userId;
	
	@Column(name = "InventoryID")
	private Integer inventoryId;

	public PurchaseLogId() {
	}

	public PurchaseLogId(Integer userID, Integer inventoryID) {
		this.userId = userID;
		this.inventoryId = inventoryID;
	}

	public Integer getUserID() {
		return userId;
	}

	public void setUserID(Integer userID) {
		this.userId = userID;
	}

	public Integer getInventoryID() {
		return inventoryId;
	}

	public void setInventoryID(Integer inventoryID) {
		this.inventoryId = inventoryID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PurchaseLogId that = (PurchaseLogId) o;
		return Objects.equals(userId, that.userId) && Objects.equals(inventoryId, that.inventoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, inventoryId);
	}

	@Override
	public String toString() {
		return "PurchaseLogId{" + "userID=" + userId + ", inventoryID=" + inventoryId + '}';
	}

}