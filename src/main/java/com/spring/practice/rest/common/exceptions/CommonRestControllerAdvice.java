package com.spring.practice.rest.common.exceptions;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonRestControllerAdvice {
    
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> notFoundExceptionHandler(Exception exception) {
        log.error(String.format("error=[%s] message=%s", exception.getClass(), exception.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
