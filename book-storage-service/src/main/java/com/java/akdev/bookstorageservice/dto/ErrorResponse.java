package com.java.akdev.bookstorageservice.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String message,
        Integer statusCode
) {
}
