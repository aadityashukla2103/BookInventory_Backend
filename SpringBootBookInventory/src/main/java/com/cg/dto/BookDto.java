package com.cg.dto;

public record BookDto(String isbn, String title, String description, String edition, Integer categoryId, Integer publisherId) {}
