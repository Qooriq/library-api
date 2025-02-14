package com.java.akdev.libraryapi.service;

import com.java.akdev.libraryapi.IntegrationTestBase;
import com.java.akdev.libraryapi.annotaion.IntegrationTest;
import com.java.akdev.libraryapi.dto.create.BookTrackerCreateDto;
import com.java.akdev.libraryapi.dto.create.UserAuthDto;
import com.java.akdev.libraryapi.dto.read.BookTrackerReadDto;
import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;
import com.java.akdev.libraryapi.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTest
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BookTrackerServiceTest extends IntegrationTestBase {


    private final BookTrackerService bookTrackerService;


    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    void findById(Long id) {
        assertTrue(bookTrackerService.findById(id).isPresent());
    }

    @Test
    void findAll() {
        Page<BookTrackerReadDto> books = bookTrackerService.findAll(1, 10);

        assertEquals(6, books.getTotalElements());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdfsadf1", "asdfsadf2", "asdfsadf3"})
    void create(String isbn) {
        var bookTrackerReadDto = bookTrackerService.create(new BookTrackerCreateDto(isbn));

        assertTrue(bookTrackerReadDto.isPresent());
    }

    @Test
    void findAllAvailableBooks() {
        var books = bookTrackerService.findAllAvailableBooks(1, 10);

        assertEquals(4, books.getTotalElements());
    }

    @Test
    void changeBookStatus() {
        assertTrue(bookTrackerService.changeBookStatus(2L, BookTrackerStatus.AVAILABLE));
    }

    @Test
    void take() {
        assertTrue(bookTrackerService.take(new UserAuthDto(UUID.fromString("0380a665-b827-49b1-9630-e716fc21cc73"),
                "aobab", "123", Role.USER), 4L, BookTrackerStatus.TAKEN));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void deleteByBookId(Long id) {
        assertTrue(bookTrackerService.deleteByBookId(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    void delete(Long id) {
        assertTrue(bookTrackerService.deleteBookTracker(id));
    }
}