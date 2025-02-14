package com.java.akdev.libraryapi.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.java.akdev.libraryapi")
@Slf4j

//TODO: WRITE EXCEPTION HANDLER
public class ExceptionHandler extends ResponseEntityExceptionHandler {


}