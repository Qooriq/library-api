package com.java.akdev.bookstorageservice.dto.read;

import com.java.akdev.bookstorageservice.enumeration.BookTrackerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookTrackerReadDto(
        @NotBlank
        String name,
        @NotNull
        BookTrackerStatus bookTrackerStatus,
        LocalDateTime tookAt,
        UUID tookBy,
        LocalDateTime returnBefore

) {
}
