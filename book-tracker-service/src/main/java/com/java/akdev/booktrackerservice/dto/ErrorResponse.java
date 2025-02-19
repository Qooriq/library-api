package com.java.akdev.booktrackerservice.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        Integer statusCode
) {
}
