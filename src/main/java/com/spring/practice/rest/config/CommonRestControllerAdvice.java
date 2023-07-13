package com.spring.practice.rest.config;

import com.spring.practice.rest.common.exception.base.NotFoundException;
import com.spring.practice.rest.common.exception.base.UnauthenticatedException;
import com.spring.practice.rest.common.exception.base.UnauthorizedException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** CommonRestControllerAdvice class. Rest API status code handler. */
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

  /**
   * Return ResponseEntity with BAD_REQUEST (400) status code.
   *
   * @param exception IllegalArgumentException
   * @return BAD_REQUEST (400)
   */
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<String> badRequestHandler(Exception exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  /**
   * Return ResponseEntity with UNAUTHORIZED (401) status code.
   *
   * @param exception UnauthenticatedException
   * @return UNAUTHORIZED (401)
   */
  @ExceptionHandler({UnauthenticatedException.class})
  public ResponseEntity<String> unauthorizedHandler(Exception exception) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  /**
   * Return ResponseEntity with FORBIDDEN (403) status code.
   *
   * @param exception UnauthorizedException
   * @return FORBIDDEN (403)
   */
  @ExceptionHandler({UnauthorizedException.class})
  public ResponseEntity<String> forbiddenHandler(Exception exception) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  /**
   * Return ResponseEntity with NOT_FOUND (404) status code.
   *
   * @param exception NoSuchElementException, NotFoundException
   * @return NOT_FOUND (404)
   */
  @ExceptionHandler({NoSuchElementException.class, NotFoundException.class})
  public ResponseEntity<String> notFoundHandler(Exception exception) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }

  /**
   * Return ResponseEntity with INTERNAL_SERVER_ERROR (500) status code.
   *
   * @param exception IllegalStateException
   * @return INTERNAL_SERVER_ERROR (500)
   */
  @ExceptionHandler({IllegalStateException.class})
  public ResponseEntity<String> internalServerErrorHandler(Exception exception) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    this.error(status, exception);
    return ResponseEntity.status(status).body(exception.getMessage());
  }
}
