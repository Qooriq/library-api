package com.java.akdev.libraryapi.repository;

import com.java.akdev.libraryapi.IntegrationTestBase;
import com.java.akdev.libraryapi.enumeration.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = {"0380a665-b827-49b1-9630-e716fc21cc73", "3f0976bd-d66f-4f29-9f2f-ed7e64f29756", "f58f0a28-726f-43cd-a3b0-8c7d921e30ec"})
    void findUser(String uuid) {
        var user = userRepository.findById(UUID.fromString(uuid));

        assertTrue(user.isPresent());
    }

    @Test
    void findAll() {
        var users = userRepository.findAll();

        assertEquals(3, users.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0380a665-b827-49b1-9630-e716fc21cc73", "3f0976bd-d66f-4f29-9f2f-ed7e64f29756", "f58f0a28-726f-43cd-a3b0-8c7d921e30ec"})
    void updateUser(String uuid) {
        var user = userRepository.findById(UUID.fromString(uuid));

        Role role = user.get().getRole();
        if (role.equals(Role.ADMIN)) {
            user.get().setRole(Role.USER);
        } else {
            user.get().setRole(Role.ADMIN);
        }

        userRepository.saveAndFlush(user.get());

        assertNotEquals(role, userRepository.findById(UUID.fromString(uuid)).get().getRole());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0d97cffb-5f2c-47e6-b863-74fbca9dca25", "e1836606-57d4-49b9-aef0-bd03590c4d20", "0d2df553-6947-43c9-b7c1-f538c6652ac7"})
    void findNoById(String uuid) {
        assertFalse(userRepository.existsById(UUID.fromString(uuid)));
    }

}