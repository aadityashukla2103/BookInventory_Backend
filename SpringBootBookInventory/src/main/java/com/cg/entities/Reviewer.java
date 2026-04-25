package com.cg.entities;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "reviewer")
public class Reviewer {

    @Id
    @Column(name = "ReviewerID")
    private Integer reviewerId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "EmployedBy")
    private String employedBy;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<BookReview> reviews;

    public Reviewer() {
    }

    public Reviewer(Integer reviewerId, String name, String employedBy) {
        this.reviewerId = reviewerId;
        this.name = name;
        this.employedBy = employedBy;
    }

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

    public List<BookReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReview> reviews) {
        this.reviews = reviews;
    }
}
