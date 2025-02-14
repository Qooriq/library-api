package com.java.akdev.libraryapi.repository;

import com.java.akdev.libraryapi.IntegrationTestBase;
import com.java.akdev.libraryapi.entity.BookTracker;
import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BookTrackerRepositoryTest extends IntegrationTestBase {

    @Autowired
    private BookTrackerRepository trackerRepository;
    @Autowired
    private BookRepository bookRepository;

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    void findById(Long id) {
        var tracker = trackerRepository.findById(id);

        assertTrue(tracker.isPresent());
    }

    @Test
    void findAll() {
        var trackers = trackerRepository.findAll();

        assertEquals(6, trackers.size());
    }

    @ParameterizedTest
    @ValueSource(longs = {7L, 8L, 9L, 10L})
    void findNoById(Long id) {
        var tracker = trackerRepository.findById(id);

        assertFalse(tracker.isPresent());
    }


    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void updateById(Long id) {
        var tracker = trackerRepository.findById(id);
        var status = tracker.get().getBookTrackerStatus();

        if (status.equals(BookTrackerStatus.AVAILABLE)) {
            tracker.get().setBookTrackerStatus(BookTrackerStatus.TAKEN);
        } else {
            tracker.get().setBookTrackerStatus(BookTrackerStatus.AVAILABLE);
        }

        trackerRepository.saveAndFlush(tracker.get());

        assertNotEquals(status, trackerRepository.findById(id).get().getBookTrackerStatus());
    }

    @Test
    void save() {
        var bt = BookTracker.builder()
                .book(bookRepository.findById(1L).get())
                .bookTrackerStatus(BookTrackerStatus.AVAILABLE)
                .build();

        trackerRepository.saveAndFlush(bt);

        assertTrue(trackerRepository.existsById(7L));
    }

}