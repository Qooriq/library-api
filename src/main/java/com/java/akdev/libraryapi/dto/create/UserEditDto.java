package com.java.akdev.libraryapi.dto.create;

import com.java.akdev.libraryapi.enumeration.UserStatus;
import jakarta.validation.constraints.Email;

import java.util.UUID;

public record UserEditDto(
        UUID id,
        @Email
        String username,
        String password,
        UserStatus status
) {
}
