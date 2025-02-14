package com.java.akdev.libraryapi.service;

import com.java.akdev.libraryapi.annotation.TransactionalService;
import com.java.akdev.libraryapi.dto.create.BookTrackerCreateDto;
import com.java.akdev.libraryapi.dto.create.UserAuthDto;
import com.java.akdev.libraryapi.dto.read.BookTrackerReadDto;
import com.java.akdev.libraryapi.entity.BookTracker;
import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;
import com.java.akdev.libraryapi.repository.BookRepository;
import com.java.akdev.libraryapi.repository.BookTrackerRepository;
import com.java.akdev.libraryapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@TransactionalService
@RequiredArgsConstructor
public class BookTrackerService {

    private final BookTrackerRepository btRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public Page<BookTrackerReadDto> findAll(Integer page, Integer size) {
        PageRequest req = PageRequest.of(page - 1, size);
        return btRepository.findAll(req)
                .map(this::toBookTrackerReadDto);
    }

    public Optional<BookTrackerReadDto> findById(Long id) {
        return btRepository.findById(id)
                .map(this::toBookTrackerReadDto);
    }

    public Optional<BookTrackerReadDto> create(BookTrackerCreateDto dto) {
        return Optional.of(dto)
                .map(obj -> {
                    var book = bookRepository.findByIsbn(dto.isbn());
                    var bookTracker = BookTracker.builder()
                            .book(book)
                            .bookTrackerStatus(BookTrackerStatus.AVAILABLE)
                            .build();
                    btRepository.saveAndFlush(bookTracker);
                    return bookTracker;
                })
                .map(this::toBookTrackerReadDto);
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

    public boolean take(UserAuthDto userAuthDto, Long id, BookTrackerStatus bookTrackerStatus) {
        return btRepository.findById(id)
                .map(bookTracker -> {
                    bookTracker.setBookTrackerStatus(bookTrackerStatus);
                    bookTracker.setUser(userRepository.findById(userAuthDto.id()).get());
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

    public boolean deleteByBookId(Long id) {
        var list = btRepository.findBookTrackerByBookId(id)
                .stream().peek(bookTracker -> {
                    bookTracker.setBookTrackerStatus(BookTrackerStatus.DELETED);
                    btRepository.saveAndFlush(bookTracker);
                })
                .toList();
        return !list.isEmpty();
    }

    private BookTrackerReadDto toBookTrackerReadDto(BookTracker bt) {
        if (bt.getUser() != null
            && bt.getTookAt() != null
            && bt.getReturnBefore() != null) {
            return new BookTrackerReadDto(bt.getBook().getName(), bt.getBookTrackerStatus(),
                    bt.getTookAt(), bt.getUser().getId(), bt.getReturnBefore());
        } else {
            return new BookTrackerReadDto(bt.getBook().getName(), bt.getBookTrackerStatus(),
                    null, null, null);
        }
    }

}
