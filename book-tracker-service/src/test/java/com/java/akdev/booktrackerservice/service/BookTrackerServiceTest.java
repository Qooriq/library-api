package com.java.akdev.booktrackerservice.service;

import com.java.akdev.booktrackerservice.IntegrationTestBase;
import com.java.akdev.booktrackerservice.dto.create.BookTrackerCreateDto;
import com.java.akdev.booktrackerservice.dto.read.BookTrackerReadDto;
import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
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
//        assertTrue(bookTrackerService.take());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdgas1"})
    void deleteByBookIsbn(String isbn) {
        assertTrue(bookTrackerService.deleteByBookIsbn(isbn));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    void delete(Long id) {
        assertTrue(bookTrackerService.deleteBookTracker(id));
    }
}