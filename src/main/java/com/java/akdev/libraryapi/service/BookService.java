package com.java.akdev.libraryapi.service;

import com.java.akdev.libraryapi.annotation.TransactionalService;
import com.java.akdev.libraryapi.dto.BookDto;
import com.java.akdev.libraryapi.enumeration.BookStatus;
import com.java.akdev.libraryapi.mapper.BookMapper;
import com.java.akdev.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@TransactionalService
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Optional<BookDto> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookDto);
    }

    public Page<BookDto> findAll(Integer page, Integer size) {
        PageRequest req = PageRequest.of(page - 1, size);
        return bookRepository.findAll(req)
                .map(bookMapper::toBookDto);

    }

    public Optional<BookDto> create(BookDto dto) {
        var book = bookMapper.toBook(dto);
        book.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.saveAndFlush(book);
        return Optional.of(book)
                .map(bookMapper::toBookDto);
    }

    public Optional<BookDto> update(Long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> {
                            bookMapper.map(book, bookDto);
                            return book;
                        }
                )
                .map(bookRepository::saveAndFlush)
                .map(bookMapper::toBookDto);
    }

    public boolean delete(Long id) {
        var book = bookRepository.findById(id);
        if (book.isPresent()) {
            book.get().setBookStatus(BookStatus.DELETED);
            bookRepository.saveAndFlush(book.get());
            return true;
        }
        return false;
    }

    public Optional<BookDto> findByIsbn(String isbn) {
        return Optional.of(bookRepository.findByIsbn(isbn))
                .map(bookMapper::toBookDto);

    }
}
