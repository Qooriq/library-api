package com.java.akdev.bookstorageservice.service;

import com.java.akdev.bookstorageservice.dto.BookDto;
import com.java.akdev.bookstorageservice.enumeration.BookStatus;
import com.java.akdev.bookstorageservice.exception.BookNotFoundException;
import com.java.akdev.bookstorageservice.mapper.BookMapper;
import com.java.akdev.bookstorageservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    public BookDto findById(Long id) {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookMapper.toBookDto(book);
    }

    @Transactional(readOnly = true)
    public Page<BookDto> findAll(Integer page, Integer size) {
        PageRequest req = PageRequest.of(page - 1, size);
        return bookRepository.findAll(req)
                .map(bookMapper::toBookDto);
    }

    @Transactional
    public BookDto create(BookDto dto) {
        var book = bookMapper.toBook(dto);
        book.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.saveAndFlush(book);
        return bookMapper.toBookDto(book);
    }

    public BookDto update(Long id, BookDto bookDto) {
        var book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookMapper.map(book, bookDto);
        return bookMapper.toBookDto(book);
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

    public BookDto findByIsbn(String isbn) {
        var book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException());
        return bookMapper.toBookDto(book);
    }
}
