package com.cg.dto;

import jakarta.validation.constraints.*;

public class ReviewerDto {

    @NotNull(message = "Reviewer ID is required")
    @Positive(message = "Reviewer ID must be greater than 0")
    private Integer reviewerId;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @Size(max = 50, message = "EmployedBy must be less than 50 characters")
    private String employedBy;

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployedBy() {
        return employedBy;
    }

    public void setEmployedBy(String employedBy) {
        this.employedBy = employedBy;
    }
}