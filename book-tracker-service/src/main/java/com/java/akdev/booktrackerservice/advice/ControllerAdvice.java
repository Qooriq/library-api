package com.java.akdev.booktrackerservice.advice;

import com.java.akdev.booktrackerservice.dto.ErrorResponse;
import com.java.akdev.booktrackerservice.exception.BookNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.status(404).body(ErrorResponse.builder()
                .message("Book not found")
                .statusCode(404).build());
    }

}
