package com.java.akdev.libraryapi.dto.create;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
