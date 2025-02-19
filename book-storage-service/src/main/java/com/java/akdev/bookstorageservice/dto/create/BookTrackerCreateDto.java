package com.java.akdev.bookstorageservice.dto.create;

import jakarta.validation.constraints.NotBlank;

public record BookTrackerCreateDto(
        @NotBlank
        String isbn
){
}
