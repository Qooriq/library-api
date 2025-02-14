package com.java.akdev.libraryapi.dto.create;

import com.java.akdev.libraryapi.enumeration.Role;

import java.util.UUID;

public record UserAuthDto(
        UUID id,
        String username,
        String password,
        Role role
) {
}
