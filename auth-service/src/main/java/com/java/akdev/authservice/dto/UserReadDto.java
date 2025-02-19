package com.java.akdev.authservice.dto;

import jakarta.validation.constraints.Email;

public record UserReadDto(
        @Email
        String username
) {
}
