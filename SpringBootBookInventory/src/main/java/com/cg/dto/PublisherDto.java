package com.cg.dto;

import jakarta.validation.constraints.*;

public class PublisherDto {

    private Integer publisherId;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @Size(max = 30, message = "City must be less than 30 characters")
    private String city;

    @NotBlank(message = "State code is required")
    @Size(min = 2, max = 2, message = "State code must be exactly 2 characters")
    private String stateCode;

    public Integer getPublisherId() { return publisherId; }

    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getStateCode() { return stateCode; }

    public void setStateCode(String stateCode) { this.stateCode = stateCode; }
}