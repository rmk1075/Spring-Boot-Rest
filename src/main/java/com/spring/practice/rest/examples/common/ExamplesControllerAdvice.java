package com.spring.practice.rest.examples.common;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages = "com.spring.practice.rest.examples")
class ControllerAdviceExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException exception) {
        log.error(String.format("NoSuchElementException. message=%s", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}

@Slf4j
@RestControllerAdvice(basePackages = "com.spring.practice.rest.examples")
class RestControllerAdviceExceptionHandler {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<?> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException exception) {
        log.error(String.format("IndexOutOfBoundsException. message=%s", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        log.error(String.format("IllegalArgumentException. message=%s", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}