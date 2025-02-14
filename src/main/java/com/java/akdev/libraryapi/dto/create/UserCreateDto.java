package com.java.akdev.libraryapi.dto.create;

import com.java.akdev.libraryapi.enumeration.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
        @Email
        String username,
        @NotBlank
        String password,
        UserStatus userStatus
) {
}
