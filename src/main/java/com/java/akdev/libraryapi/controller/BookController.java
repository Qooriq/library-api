package com.java.akdev.libraryapi.controller;

import com.java.akdev.libraryapi.annotation.ValidatedController;
import com.java.akdev.libraryapi.dto.BookDto;
import com.java.akdev.libraryapi.dto.create.BookTrackerCreateDto;
import com.java.akdev.libraryapi.dto.read.PageResponse;
import com.java.akdev.libraryapi.feignclient.PostTrackerClient;
import com.java.akdev.libraryapi.service.BookService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;

@ValidatedController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final PostTrackerClient postTrackerClient;


    @GetMapping
    public ResponseEntity<PageResponse<BookDto>> findAll(@RequestParam(defaultValue = "1") @Min(1) Integer page,
                                                         @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size) {
        return ok().body(PageResponse.of(bookService.findAll(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(obj -> ok()
                        .body(obj))
                .orElseGet(notFound()::build);
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestHeader(value = "Authorization") String token,
                                          @RequestBody BookDto bookDto) {
        return bookService.create(bookDto)
                .map(obj -> {
                    postTrackerClient.addNewTracker(token, new BookTrackerCreateDto(bookDto.isbn()));
                    return ok().body(obj);
                })
                .orElseGet(notFound()::build);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestHeader("Authorization") String token,
                                           @RequestParam Long id) {
        boolean res = bookService.delete(id);
        postTrackerClient.deleteBookTracker(token, id);
        return res ? noContent().build() : notFound().build();
    }

    @PutMapping
    public ResponseEntity<BookDto> update(@RequestParam Long id,
                                          @RequestBody BookDto bookDto) {
        return bookService.update(id, bookDto)
                .map(obj -> ok().body(obj))
                .orElseGet(notFound()::build);
    }

    @GetMapping("/isbn")
    public ResponseEntity<BookDto> findByIsbn(@RequestParam String isbn) {
        return bookService.findByIsbn(isbn)
                .map(obj -> ok()
                        .body(obj))
                .orElseGet(notFound()::build);
    }

}
