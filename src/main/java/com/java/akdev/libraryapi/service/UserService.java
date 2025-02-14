package com.java.akdev.libraryapi.service;

import com.java.akdev.libraryapi.annotation.TransactionalService;
import com.java.akdev.libraryapi.dto.create.UserEditDto;
import com.java.akdev.libraryapi.dto.create.UserLoginDto;
import com.java.akdev.libraryapi.dto.read.UserReadDto;
import com.java.akdev.libraryapi.dto.read.UserRegisterDto;
import com.java.akdev.libraryapi.entity.User;
import com.java.akdev.libraryapi.enumeration.Role;
import com.java.akdev.libraryapi.enumeration.UserStatus;
import com.java.akdev.libraryapi.mapper.UserMapper;
import com.java.akdev.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@TransactionalService
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserReadDto> findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toUserReadDto);
    }

    public Page<UserReadDto> findAll(Integer page, Integer size) {
        PageRequest req = PageRequest.of(page - 1, size);
        return userRepository.findAll(req)
                .map(userMapper::toUserReadDto);
    }

    public boolean delete(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUserStatus(UserStatus.DELETED);
                    userRepository.saveAndFlush(user);
                    return true;
                })
                .orElse(false);
    }

    public Optional<UserReadDto> update(UserEditDto dto) {
        return userRepository.findById(dto.id())
                .map(user -> {
                    user.setId(dto.id());
                    user.setUsername(dto.username());
                    user.setRole(Role.USER);
                    user.setUserStatus(dto.status());
                    user.setPassword(passwordEncoder.encode(dto.password()));
                    return user;
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::toUserReadDto);
    }

    public Optional<UserRegisterDto> findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserRegisterDto);
    }

    public UUID create(UserLoginDto dto) {
        return Optional.of(userMapper.toUser(dto))
                .map(user -> {
                    UUID id = UUID.randomUUID();
                    user.setId(id);
                    user.setRole(Role.USER);
                    user.setUserStatus(UserStatus.ACTIVE);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return user;
                })
                .map(userRepository::saveAndFlush)
                .map(User::getId)
                .orElseThrow();
    }

    public UserDetailsService userDetailsService() {
        return this::loadUserByUsername;
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user:" + username));
    }
}
