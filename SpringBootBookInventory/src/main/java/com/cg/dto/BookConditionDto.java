package com.cg.dto;

import jakarta.validation.constraints.*;

public class BookConditionDto {

    @NotNull(message = "Rank is required")
    @Min(value = 1, message = "Rank must be greater than 0")
    private Integer ranks;

    @NotBlank(message = "Description is required")
    @Size(max = 50, message = "Description must be less than 50 characters")
    private String description;

    @NotBlank(message = "Full description is required")
    @Size(max = 255, message = "Full description must be less than 255 characters")
    private String fullDescription;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    public Integer getRanks() {
        return ranks;
    }

    public void setRanks(Integer ranks) {
        this.ranks = ranks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}