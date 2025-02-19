package com.java.akdev.booktrackerservice.dto;

import com.java.akdev.booktrackerservice.enumeration.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookDto(
        @NotBlank
        String isbn,
        @NotBlank
        String name,
        @NotNull
        Genre genre,
        String description,
        @NotBlank
        String author
) {
}
