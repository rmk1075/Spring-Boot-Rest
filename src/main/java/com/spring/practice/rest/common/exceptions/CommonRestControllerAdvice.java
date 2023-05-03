package com.spring.practice.rest.common.exceptions;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonRestControllerAdvice {

  private void error(HttpStatus status, Exception exception) {
    log.error(
        String.format(
            "[%s] error=%s message=%s",
            status.getReasonPhrase(), exception.getClass(), exception.getMessage()),
        exception);
  }

  // BAD_REQUEST (400)
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<String> badRequestHandler(Exception exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  // FORBIDDEN (403)
  @ExceptionHandler({UnauthorizedException.class})
  public ResponseEntity<String> unAuthorizedExceptionHandler(Exception exception) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  // NOT_FOUND (404)
  @ExceptionHandler({NoSuchElementException.class})
  public ResponseEntity<String> notFoundExceptionHandler(Exception exception) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  // INTERNAL_SERVER_ERROR (500)
  @ExceptionHandler({IllegalStateException.class})
  public ResponseEntity<String> internalServerErrorHandler(Exception exception) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }
}
