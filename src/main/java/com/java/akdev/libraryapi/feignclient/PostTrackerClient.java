package com.java.akdev.libraryapi.feignclient;

import com.java.akdev.libraryapi.dto.create.BookTrackerCreateDto;
import com.java.akdev.libraryapi.dto.read.BookTrackerReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "postTracker", url = "localhost:8080/book-tracker/")
public interface PostTrackerClient {

    @PostMapping()
    ResponseEntity<BookTrackerReadDto> addNewTracker(@RequestHeader(value = "Authorization") String token,
            @RequestBody BookTrackerCreateDto bookDto);

    @DeleteMapping("delete-by-book-id")
    ResponseEntity<BookTrackerReadDto> deleteBookTracker(@RequestHeader(value = "Authorization") String token,
                                                     @RequestParam Long bookId);
}
