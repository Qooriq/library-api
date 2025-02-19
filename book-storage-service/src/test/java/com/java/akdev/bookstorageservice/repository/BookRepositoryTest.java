package com.java.akdev.bookstorageservice.repository;

import com.java.akdev.bookstorageservice.IntegrationTestBase;
import com.java.akdev.bookstorageservice.entity.Book;
import com.java.akdev.bookstorageservice.enumeration.BookStatus;
import com.java.akdev.bookstorageservice.enumeration.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BookRepositoryTest extends IntegrationTestBase {

    @Autowired
    private BookRepository bookRepository;

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void findById(Long id) {
        var book = bookRepository.findById(id);

        assertTrue(book.isPresent());
    }

    @Test
    void findAll() {
        PageRequest req = PageRequest.of(0, 10);
        var books = bookRepository.findAll(req);

        assertEquals(3, books.getTotalElements());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void update(Long id) {
        String descr;
        var book = bookRepository.findById(id);
        descr = book.get().getDescription();

        book.get().setDescription("aboba");

        bookRepository.saveAndFlush(book.get());

        assertNotEquals(bookRepository.findById(id).get().getDescription(), descr);
    }


    @ParameterizedTest
    @ValueSource(longs = {4L, 5L, 6L})
    void findNoById(Long id) {
        var book = bookRepository.findById(id);

        assertFalse(book.isPresent());
    }

    @Test
    void save() {
        var book = Book.builder()
                .isbn("aboba")
                .bookStatus(BookStatus.AVAILABLE)
                .genre(Genre.ADVENTURE_FICTION)
                .name("Hi")
                .description("Hi")
                .author("Yab")
                .build();

        bookRepository.saveAndFlush(book);
        var books = bookRepository.findAll();

        assertEquals(4, books.size());
    }
}