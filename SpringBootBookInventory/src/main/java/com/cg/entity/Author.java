package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {

	@Id
	@Column(name = "authorID")
	private Integer authorID;

	@Column(name = "LastName", nullable = false)
	private String lastName;

	@Column(name = "FirstName", nullable = false)
	private String firstName;

	@Column(name = "Photo", length = 1)
	private String photo;

	public Author() {
	}

	public Integer getAuthorID() {
		return authorID;
	}

	public void setAuthorID(Integer authorID) {
		this.authorID = authorID;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}