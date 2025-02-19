package com.java.akdev.booktrackerservice.dto.create;

import jakarta.validation.constraints.NotBlank;

public record BookTrackerCreateDto(
        @NotBlank
        String isbn
){
}
