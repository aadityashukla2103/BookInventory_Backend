package com.cg.dto;

public class ReviewerDto {

    private Integer reviewerId;
    private String name;
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