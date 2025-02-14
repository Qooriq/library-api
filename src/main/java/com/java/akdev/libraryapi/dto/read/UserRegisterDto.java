package com.java.akdev.libraryapi.dto.read;

import com.java.akdev.libraryapi.enumeration.UserStatus;

import java.util.UUID;

public record UserRegisterDto(
        UUID id,
        String username,
        String password,
        UserStatus userStatus
) {
}
