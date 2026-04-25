package com.cg.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchaselog")
public class PurchaseLog {

    @EmbeddedId
    private PurchaseLogId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne
    @MapsId("inventoryID")
    @JoinColumn(name = "InventoryID")
    private Inventory inventory;

    public PurchaseLogId getId() {
        return id;
    }

    public void setId(PurchaseLogId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}