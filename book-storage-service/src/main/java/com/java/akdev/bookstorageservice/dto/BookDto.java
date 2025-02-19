package com.java.akdev.bookstorageservice.dto;

import com.java.akdev.bookstorageservice.enumeration.Genre;
import jakarta.validation.constraints.NotBlank;

public record BookDto(
        @NotBlank
        String isbn,
        @NotBlank
        String name,
        @NotBlank
        Genre genre,
        String description,
        @NotBlank
        String author
) {
}
