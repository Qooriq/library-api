package com.java.akdev.libraryapi.controller;

import com.java.akdev.libraryapi.annotation.ValidatedController;
import com.java.akdev.libraryapi.dto.create.BookTrackerCreateDto;
import com.java.akdev.libraryapi.dto.create.BookTrackerEditDto;
import com.java.akdev.libraryapi.dto.create.UserAuthDto;
import com.java.akdev.libraryapi.dto.read.BookTrackerReadDto;
import com.java.akdev.libraryapi.dto.read.PageResponse;
import com.java.akdev.libraryapi.enumeration.BookTrackerStatus;
import com.java.akdev.libraryapi.service.BookTrackerService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@ValidatedController
@RequestMapping("book-tracker")
@RequiredArgsConstructor
public class BookTrackerController {

    private final BookTrackerService bookTrackerService;

    @GetMapping("/findAll")
    public ResponseEntity<PageResponse<BookTrackerReadDto>> getAllBooks(@RequestParam @Min(1) Integer page,
                                                                        @RequestParam @Min(1) @Max(100) Integer size,
                                                                        @AuthenticationPrincipal UserAuthDto userAuthDto) {
        return ok().body(PageResponse.of(bookTrackerService.findAll(page, size)));
    }

    @PostMapping
    public ResponseEntity<BookTrackerReadDto> createBookTracker(@RequestBody BookTrackerCreateDto dto) {
        return bookTrackerService.create(dto)
                .map(obj -> ok().body(obj))
                .orElseGet(notFound()::build);
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookTrackerReadDto>> getAllAvailableBooksBooks(@RequestParam @Min(1) Integer page,
                                                                                      @RequestParam @Min(1) @Max(100) Integer size,
                                                                                      @AuthenticationPrincipal UserAuthDto userAuthDto) {
        return ok().body(PageResponse.of(bookTrackerService.findAllAvailableBooks(page, size)));
    }

    @PutMapping
    public ResponseEntity<Boolean> changeBookStatus(@RequestBody BookTrackerEditDto bookTrackerEditDto) {
        return ok().body(bookTrackerService
                .changeBookStatus(bookTrackerEditDto.id(), bookTrackerEditDto.bookTrackerStatus()));
    }

    @PutMapping("/take")
    public ResponseEntity<Boolean> changeBookStatus(@AuthenticationPrincipal UserAuthDto userAuthDto,
                                                    @RequestParam Long id) {
        return ok().body(bookTrackerService
                .take(userAuthDto, id, BookTrackerStatus.TAKEN));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBookTracker(@RequestParam Long id) {
        return bookTrackerService.deleteBookTracker(id) ? noContent().build() : notFound().build();
    }

    @DeleteMapping("/delete-by-book-id")
    public ResponseEntity<Void> deleteByBookId(@RequestParam Long bookId) {
        return bookTrackerService.deleteByBookId(bookId) ? noContent().build() : notFound().build();
    }


}
