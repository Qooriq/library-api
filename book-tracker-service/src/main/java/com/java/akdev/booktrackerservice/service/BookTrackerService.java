package com.java.akdev.booktrackerservice.service;

import com.java.akdev.booktrackerservice.dto.create.BookTrackerCreateDto;
import com.java.akdev.booktrackerservice.dto.read.BookTrackerReadDto;
import com.java.akdev.booktrackerservice.entity.BookTracker;
import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import com.java.akdev.booktrackerservice.repository.BookTrackerRepository;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.text.ParseException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookTrackerService {

    private final BookTrackerRepository btRepository;

    public Page<BookTrackerReadDto> findAll(Integer page, Integer size) {
        PageRequest req = PageRequest.of(page - 1, size);
        return btRepository.findAll(req)
                .map(this::toBookTrackerReadDto);
    }



    public Optional<BookTrackerReadDto> findById(Long id) {
        return btRepository.findById(id)
                .map(this::toBookTrackerReadDto);
    }

    public BookTrackerReadDto create(BookTrackerCreateDto dto) {
        var bookTracker = BookTracker.builder()
                .isbn(dto.isbn())
                .bookTrackerStatus(BookTrackerStatus.AVAILABLE)
                .build();
        return toBookTrackerReadDto(btRepository.saveAndFlush(bookTracker));
    }

    public Page<BookTrackerReadDto> findAllAvailableBooks(Integer page, Integer size) {
        PageRequest req = PageRequest.of(page - 1, size);
        return btRepository.findAllByBookTrackerStatus(BookTrackerStatus.AVAILABLE, req)
                .map(this::toBookTrackerReadDto);
    }

    public boolean changeBookStatus(Long id, BookTrackerStatus bookTrackerStatus) {
        return btRepository.findById(id)
                .map(bookTracker -> {
                    bookTracker.setBookTrackerStatus(bookTrackerStatus);
                    btRepository.saveAndFlush(bookTracker);
                    return true;
                })
                .orElse(false);
    }

    public boolean take(String token, Long id, BookTrackerStatus bookTrackerStatus) {
        return btRepository.findById(id)
                .map(bookTracker -> {
                    String username = "";
                    try {
                        var signedJwt = SignedJWT.parse(token.substring(7));
                        var claimsSet = signedJwt.getJWTClaimsSet();
                        username = (String) claimsSet.getClaim("preferred_username");
                    } catch (ParseException e) {
                        log.error(Arrays.toString(e.getStackTrace()));
                    }
                    bookTracker.setUsername(username);
                    bookTracker.setBookTrackerStatus(bookTrackerStatus);
                    bookTracker.setTookAt(LocalDateTime.now());
                    bookTracker.setReturnBefore(LocalDateTime.now().plusDays(7));
                    btRepository.saveAndFlush(bookTracker);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteBookTracker(Long id) {
        return btRepository.findById(id)
                .map(bookTracker -> {
                    bookTracker.setBookTrackerStatus(BookTrackerStatus.DELETED);
                    btRepository.saveAndFlush(bookTracker);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteByBookIsbn(String isbn) {
        var list = btRepository.findBookTrackerByIsbn(isbn)
                .stream().peek(bookTracker -> {
                    bookTracker.setBookTrackerStatus(BookTrackerStatus.DELETED);
                    btRepository.saveAndFlush(bookTracker);
                })
                .toList();
        return !list.isEmpty();
    }

    private BookTrackerReadDto toBookTrackerReadDto(BookTracker bookTracker) {
        if (bookTracker.getUsername() != null) {
            return new BookTrackerReadDto(bookTracker.getIsbn(),
                    bookTracker.getBookTrackerStatus(), bookTracker.getTookAt(),
                    bookTracker.getUsername(), bookTracker.getReturnBefore());
        }
        return new BookTrackerReadDto(bookTracker.getIsbn(),
                bookTracker.getBookTrackerStatus(), null,
                null, null);
    }


}
