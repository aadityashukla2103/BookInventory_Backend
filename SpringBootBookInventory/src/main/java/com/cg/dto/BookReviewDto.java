package com.cg.dto;

public record BookReviewDto(String isbn, Integer reviewerId, Integer rating, String comments) {}
