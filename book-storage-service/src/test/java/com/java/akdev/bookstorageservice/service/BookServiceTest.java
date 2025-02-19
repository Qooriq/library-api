package com.java.akdev.bookstorageservice.service;

import com.java.akdev.bookstorageservice.IntegrationTestBase;
import com.java.akdev.bookstorageservice.dto.BookDto;
import com.java.akdev.bookstorageservice.enumeration.Genre;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BookServiceTest extends IntegrationTestBase {

    private final BookService bookService;


    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void findById() {
        assertNotNull(bookService.findById(1L));
    }

    @Test
    void findAll() {
        var books = bookService.findAll(1, 10);

        assertEquals(3, books.getTotalElements());
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdfsadf4", "asdfsadf5", "asdfsadf6"})
    void create(String isbn) {
        var bookDto = new BookDto(isbn, "asd", Genre.BIOGRAPHY, "aad", "Ya");
        var book = bookService.create(bookDto);

        assertNotNull(book);
    }

//    @Test
//    void update() {
//        var bookDto = bookService.findById(1L);
//        var update = bookService.update(1L, new BookDto(bookDto.isbn(), "asdf", Genre.FANTASY, "asd", "gsdfg"));
//        assertNotEquals(bookDto, update);
//    }

    @Test
    void delete() {
        assertTrue(bookService.delete(1L));
    }

    @ParameterizedTest
    @ValueSource(strings = {"asdfsadf1", "asdfsadf2", "asdfsadf3"})
    void findByIsbn(String isbn) {
        assertNotNull(bookService.findByIsbn(isbn));
    }
}