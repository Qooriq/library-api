package com.java.akdev.libraryapi.dto.read;

import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookTrackerReadDto(
        String name,
        BookTrackerStatus bookTrackerStatus,
        LocalDateTime tookAt,
        UUID tookBy,
        LocalDateTime returnBefore

) {
}
