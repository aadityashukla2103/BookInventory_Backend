package com.cg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthorDto {

    @NotNull(message = "Author ID is required")
    private Integer authorID;

    @NotBlank(message = "Last name is required")
    @Size(max = 40, message = "Last name must be less than 40 characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(max = 40, message = "First name must be less than 40 characters")
    private String firstName;

    @Size(max = 1, message = "Photo must be only 1 character")
    private String photo;

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