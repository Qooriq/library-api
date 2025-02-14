package com.java.akdev.libraryapi.controller;

import com.java.akdev.libraryapi.annotation.ValidatedController;
import com.java.akdev.libraryapi.dto.create.UserLoginDto;
import com.java.akdev.libraryapi.dto.create.UserEditDto;
import com.java.akdev.libraryapi.dto.read.JwtResponse;
import com.java.akdev.libraryapi.dto.read.PageResponse;
import com.java.akdev.libraryapi.dto.read.UserReadDto;
import com.java.akdev.libraryapi.service.AuthenticationService;
import com.java.akdev.libraryapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

@ValidatedController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;


    @GetMapping
    public ResponseEntity<PageResponse<UserReadDto>> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        return ok().body(PageResponse.of(userService.findAll(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> findById(@PathVariable("id") String id) {
        return userService.findById(UUID.fromString(id))
                .map(obj -> ok()
                        .body(obj))
                .orElse(notFound().build());
    }

    @PutMapping
    public ResponseEntity<UserReadDto> update(@RequestBody UserEditDto dto) {
        return userService.update(dto)
                .map(obj -> ok().body(obj))
                .orElse(notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody UUID id) {
        return userService.delete(id) ? noContent().build() : notFound().build();
    }

    @PostMapping("/registration")
    public JwtResponse create(@RequestBody UserLoginDto dto) {
        return authenticationService.signUp(dto);
    }

    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody UserLoginDto dto) {
        return authenticationService.signIn(dto);
    }


}
