package com.java.akdev.authservice.service;

import com.java.akdev.authservice.dto.UserReadDto;
import com.java.akdev.authservice.dto.UserRegisterDto;
import com.java.akdev.authservice.entity.User;
import com.java.akdev.authservice.repository.UserRepository;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${application.keycloak.realm.app}")
    private String realm;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Keycloak keycloak;


    public UserReadDto registerUser(UserRegisterDto dto) {
        var registrationForm = createUserFromRegistrationForm(dto);

        try (var resp = keycloak.realm(realm)
                .users()
                .create(registrationForm)) {
            if (resp.getStatus() >= 300) {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        var user = getUserByUsername(dto.username());

        var creds = new CredentialRepresentation();
        creds.setTemporary(false);
        creds.setType("password");
        creds.setValue(dto.password());

        keycloak.realm(realm)
                .users()
                .get(user.getId())
                .resetPassword(creds);

        var userEntity = User.builder().id(UUID.randomUUID())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        userRepository.saveAndFlush(userEntity);

        return new UserReadDto(dto.username());
    }

    @Nullable
    public UserRepresentation getUserByUsername(@NotNull String username) {
        var list = keycloak.realm(realm)
                .users()
                .search(username);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getFirst();
    }


    private UserRepresentation createUserFromRegistrationForm(UserRegisterDto dto) {
        var user = new UserRepresentation();
        user.setUsername(dto.username().toLowerCase());
        user.setEmail(dto.username().toLowerCase());
        user.setEnabled(true);

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());

        return user;
    }

}
