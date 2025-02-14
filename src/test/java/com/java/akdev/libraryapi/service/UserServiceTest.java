package com.java.akdev.libraryapi.service;

import com.java.akdev.libraryapi.IntegrationTestBase;
import com.java.akdev.libraryapi.annotaion.IntegrationTest;
import com.java.akdev.libraryapi.dto.create.UserEditDto;
import com.java.akdev.libraryapi.dto.create.UserLoginDto;
import com.java.akdev.libraryapi.enumeration.UserStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"0380a665-b827-49b1-9630-e716fc21cc73", "3f0976bd-d66f-4f29-9f2f-ed7e64f29756", "f58f0a28-726f-43cd-a3b0-8c7d921e30ec"})
    void findById(String id) {
        assertTrue(userService.findById(UUID.fromString(id)).isPresent());
    }

    @Test
    void findAll() {
        assertEquals(3, userService.findAll(1, 10).getTotalElements());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0380a665-b827-49b1-9630-e716fc21cc73", "3f0976bd-d66f-4f29-9f2f-ed7e64f29756", "f58f0a28-726f-43cd-a3b0-8c7d921e30ec"})
    void delete(String id) {
        assertTrue(userService.delete(UUID.fromString(id)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0380a665-b827-49b1-9630-e716fc21cc73", "3f0976bd-d66f-4f29-9f2f-ed7e64f29756", "f58f0a28-726f-43cd-a3b0-8c7d921e30ec"})
    void update(String id) {
        var dto = new UserEditDto(UUID.fromString(id), "a@gmail.com", "123", UserStatus.ACTIVE);
        assertTrue(userService.update(dto).isPresent());
    }

    @Test
    void create() {
        var dto = new UserLoginDto("a@gmail.com", "123");
        assertFalse(userService.create(dto).toString().isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"gadd@gmail.com", "as@gmail.com", "tha@gmail.com"})
    void findUserByUsername() {
        assertTrue(userService.findUserByUsername("gadd@gmail.com").isPresent());
    }
}