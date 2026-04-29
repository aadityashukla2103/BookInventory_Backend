package com.cg.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "publisher")
public class Publisher {
	@Id
	@Column(name = "PublisherID")
	private Integer publisherId;

	@Column(name = "Name", length = 50, nullable = false)
	private String name;

	@Column(name = "City", length = 30)
	private String city;

	@ManyToOne
	@JoinColumn(name = "StateCode")
	private State state;

	@OneToMany(mappedBy = "publisher")
	private List<Book> books;

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}