package com.java.akdev.authservice.service;

import com.java.akdev.authservice.IntegrationTestBase;
import com.java.akdev.authservice.dto.UserRegisterDto;
import com.java.akdev.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Transactional
@Rollback
@Testcontainers
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserServiceTest extends IntegrationTestBase {

    private final UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"gf@gmail.com", "fg@gmail.com", "hgfgj@gmail.com"})
    public void registration(String username) {
        var userReadDto = userService.registerUser(new UserRegisterDto(username, "123", "a", "b"));
        assertSame(userReadDto.username(), username);
    }

}
