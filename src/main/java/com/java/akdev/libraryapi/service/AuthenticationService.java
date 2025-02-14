package com.java.akdev.libraryapi.service;

import com.java.akdev.libraryapi.dto.create.UserLoginDto;
import com.java.akdev.libraryapi.dto.read.JwtResponse;
import com.java.akdev.libraryapi.entity.User;
import com.java.akdev.libraryapi.enumeration.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtResponse signUp(UserLoginDto dto) {
        var UUID = userService.create(dto);

        var user = User.builder()
                .id(UUID)
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();

        var jwt = jwtService.generateToken(user);
        return new JwtResponse(jwt);
    }

    public JwtResponse signIn(UserLoginDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        var user = userService.findUserByUsername(dto.username());

        if (!user.get().userStatus().equals(UserStatus.ACTIVE)) {
            throw new RuntimeException();
        }

        var id = user.get().id();
        var username = user.get().username();
        var password = user.get().password();

        var jwt = jwtService.generateToken(User.builder()
                .username(username)
                .id(id)
                .password(password)
                .build());
        return new JwtResponse(jwt);
    }


}
