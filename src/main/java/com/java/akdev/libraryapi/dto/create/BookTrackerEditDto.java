package com.java.akdev.libraryapi.dto.create;

import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;

public record BookTrackerEditDto (
        Long id,
        BookTrackerStatus bookTrackerStatus
) {
}
