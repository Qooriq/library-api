package com.java.akdev.booktrackerservice.controller;

import com.java.akdev.booktrackerservice.dto.create.BookTrackerCreateDto;
import com.java.akdev.booktrackerservice.dto.create.BookTrackerEditDto;
import com.java.akdev.booktrackerservice.dto.read.BookTrackerReadDto;
import com.java.akdev.booktrackerservice.dto.read.PageResponse;
import com.java.akdev.booktrackerservice.enumeration.BookTrackerStatus;
import com.java.akdev.booktrackerservice.feignclient.BookClient;
import com.java.akdev.booktrackerservice.service.BookTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("api/v1/book-tracker")
@RequiredArgsConstructor
public class BookTrackerController {

    private final BookTrackerService bookTrackerService;
    private final BookClient bookClient;

    @GetMapping("/book-trackers")
    public ResponseEntity<PageResponse<BookTrackerReadDto>> getAllBooks(@RequestParam Integer page,
                                                                        @RequestParam Integer size) {
        return ok().body(PageResponse.of(bookTrackerService.findAll(page, size)));
    }

    @PostMapping
    public ResponseEntity<BookTrackerReadDto> createBookTracker(@RequestHeader("Authorization") String token,
                                                                @RequestBody BookTrackerCreateDto dto) {

        var book = bookClient.getBookByIsbn(token, dto.isbn());
        return ResponseEntity.status(200).body(bookTrackerService.create(dto));
    }


    @GetMapping
    public ResponseEntity<PageResponse<BookTrackerReadDto>> getAllAvailableBooksBooks(@RequestParam Integer page,
                                                                                      @RequestParam Integer size) {
        return ok().body(PageResponse.of(bookTrackerService.findAllAvailableBooks(page, size)));
    }

    @PutMapping
    public ResponseEntity<Boolean> changeBookStatus(@RequestBody BookTrackerEditDto bookTrackerEditDto) {
        return ok().body(bookTrackerService
                .changeBookStatus(bookTrackerEditDto.id(), bookTrackerEditDto.bookTrackerStatus()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> changeBookStatus(@RequestHeader("Authorization") String token,
                                                    @PathVariable Long id) {
        return ok().body(bookTrackerService
                .take(token, id, BookTrackerStatus.TAKEN));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookTracker(@PathVariable Long id) {
        return bookTrackerService.deleteBookTracker(id) ? noContent().build() : notFound().build();
    }

    @DeleteMapping("/book/{isbn}")
    public ResponseEntity<Void> deleteByBookIsbn(@PathVariable String isbn) {
        return bookTrackerService.deleteByBookIsbn(isbn) ? noContent().build() : notFound().build();
    }


}
