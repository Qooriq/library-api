package com.java.akdev.authservice.controller;

import com.java.akdev.authservice.dto.UserReadDto;
import com.java.akdev.authservice.dto.UserRegisterDto;
import com.java.akdev.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserReadDto> createUser(@RequestBody UserRegisterDto user) {
        return ResponseEntity.status(201).body(userService.registerUser(user));
    }
}
