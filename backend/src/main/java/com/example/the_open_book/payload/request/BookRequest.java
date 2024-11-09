package com.example.the_open_book.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * BookRequest
 */
public record BookRequest(
    @NotBlank String title,
    @NotBlank String authorName,
    @NotBlank byte [] bookCover,
    @NotBlank String isbn,
    @NotBlank String synopsis) {
}
