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

    private void error(HttpStatus status, Exception exception) {
        log.error(
            String.format(
                "[%s] error=%s message=%s",
                status.getReasonPhrase(),
                exception.getClass(),
                exception.getMessage()
            ),
            exception
        );
    }
    
    // BAD_REQUEST (400)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> badRequestHandler(Exception exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        this.error(status, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    // NOT_FOUND (404)
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<?> notFoundExceptionHandler(Exception exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        this.error(status, exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // INTERNAL_SERVER_ERROR (500)
    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<?> internalServerErrorHandler(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.error(status, exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
