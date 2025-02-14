package com.java.akdev.libraryapi.dto;

import com.java.akdev.libraryapi.enumeration.Genre;

public record BookDto(
        String isbn,
        String name,
        Genre genre,
        String description,
        String author
) {
}
