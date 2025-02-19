package com.java.akdev.bookstorageservice.controller;

import com.java.akdev.bookstorageservice.dto.BookDto;
import com.java.akdev.bookstorageservice.dto.PageResponse;
import com.java.akdev.bookstorageservice.feignclient.PostTrackerClient;
import com.java.akdev.bookstorageservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final PostTrackerClient postTrackerClient;


    @GetMapping
    public ResponseEntity<PageResponse<BookDto>> findAll(@RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.status(201).body(PageResponse.of(bookService.findAll(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(bookService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestHeader(value = "Authorization") String token,
                                          @RequestBody BookDto bookDto) {
        return ResponseEntity.status(201).body(bookService.create(bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@RequestHeader("Authorization") String token,
                                           @PathVariable Long id) {
        var dto = bookService.findById(id);
        bookService.delete(id);
        postTrackerClient.deleteBookTracker(token, dto.isbn());
        return noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable Long id,
                                          @RequestBody BookDto bookDto) {
        return ResponseEntity.status(200).body(bookService.update(id, bookDto));
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<BookDto> findByIsbn(@PathVariable String isbn) {
        return ResponseEntity.status(200).body(bookService.findByIsbn(isbn));
    }

}
