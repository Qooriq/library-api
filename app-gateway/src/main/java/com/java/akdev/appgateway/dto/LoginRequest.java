package com.java.akdev.appgateway.dto;

public record LoginRequest (
        String username,
        String password
) {
}
