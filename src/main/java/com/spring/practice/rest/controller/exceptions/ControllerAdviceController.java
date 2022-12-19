package com.spring.practice.rest.controller.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/exceptions/controller-advice")
public class ControllerAdviceController {
    
    @GetMapping("NoSuchElementException")
    public void noSuchElementException() {
        throw new NoSuchElementException("ControllerAdviceController - NoSuchElementException");
    }

    @GetMapping("IndexOutOfBoundsException")
    public void indexOutOfBoundsException() {
        throw new IndexOutOfBoundsException("ControllerAdviceController - IndexOutOfBoundsException");
    }
}

@Slf4j
@ControllerAdvice
class ControllerAdviceExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException exception) {
        log.error(String.format("NoSuchElementException. message=%s", exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}

@Slf4j
@RestControllerAdvice
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