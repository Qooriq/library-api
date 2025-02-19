package com.java.akdev.booktrackerservice.dto.create;

import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import jakarta.validation.constraints.NotNull;

public record BookTrackerEditDto (
        Long id,
        @NotNull
        BookTrackerStatus bookTrackerStatus
) {
}
