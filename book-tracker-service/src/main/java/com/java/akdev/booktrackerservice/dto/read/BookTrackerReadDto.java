package com.java.akdev.booktrackerservice.dto.read;

import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookTrackerReadDto(
        @NotBlank
        String isbn,
        @NotNull
        BookTrackerStatus bookTrackerStatus,
        LocalDateTime tookAt,
        String tookBy,
        LocalDateTime returnBefore

) {
}
