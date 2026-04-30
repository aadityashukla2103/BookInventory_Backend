package com.cg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "inventory")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "InventoryID")
	private Integer inventoryID;

	@ManyToOne
	@JoinColumn(name = "ISBN")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "Ranks")
	private BookCondition bookCondition;

	@Column(name = "Purchased")
	private Boolean purchased;

	public Integer getInventoryID() {
		return inventoryID;
	}

	public void setInventoryID(Integer inventoryID) {
		this.inventoryID = inventoryID;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookCondition getBookCondition() {
		return bookCondition;
	}

	public void setBookCondition(BookCondition bookCondition) {
		this.bookCondition = bookCondition;
	}

	public Boolean getPurchased() {
		return purchased;
	}

	public void setPurchased(Boolean purchased) {
		this.purchased = purchased;
	}

}
