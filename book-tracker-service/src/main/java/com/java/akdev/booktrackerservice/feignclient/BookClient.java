package com.java.akdev.booktrackerservice.feignclient;

import com.java.akdev.booktrackerservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "bookClient", url = "localhost:8080/api/v1/books")
public interface BookClient {

    @GetMapping("/book/{isbn}")
    ResponseEntity<BookDto> getBookByIsbn(@RequestHeader("Authorization") String token,
                                          @PathVariable("isbn") String isbn);
}
