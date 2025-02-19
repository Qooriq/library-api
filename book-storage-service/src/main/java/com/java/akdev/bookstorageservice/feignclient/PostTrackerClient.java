package com.java.akdev.bookstorageservice.feignclient;

import com.java.akdev.bookstorageservice.dto.create.BookTrackerCreateDto;
import com.java.akdev.bookstorageservice.dto.read.BookTrackerReadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "postTracker", url = "localhost:8080/api/v1/book-tracker")
public interface PostTrackerClient {

    @PostMapping()
    ResponseEntity<BookTrackerReadDto> addNewTracker(@RequestHeader(value = "Authorization") String token,
            @RequestBody BookTrackerCreateDto bookDto);

    @DeleteMapping("/book/{isbn}")
    ResponseEntity<BookTrackerReadDto> deleteBookTracker(@RequestHeader(value = "Authorization") String token,
                                                     @PathVariable String isbn);
}
