package com.spring.practice.rest.examples.controller;

import com.spring.practice.rest.examples.common.CustomException;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ExceptionHandler()
 *
 * <p>해당 클래스에서 발생하는 error 에 처리를 위한 어노테이션 어노테이션에 지정된 error 가 발생하면 어노테이션이 설정된 메서드가 실행된다.
 */
@Slf4j
@RestController
@RequestMapping("/exceptions/exception-handler")
public class ExceptionHandlerController {

  @GetMapping("NoSuchElementException")
  public void noSuchElementException() {
    throw new NoSuchElementException("ControllerAdviceController - NoSuchElementException");
  }

  @GetMapping("IndexOutOfBoundsException")
  public void indexOutOfBoundsException() {
    throw new IndexOutOfBoundsException("ControllerAdviceController - IndexOutOfBoundsException");
  }

  @GetMapping("/RuntimeException")
  public void runtimeException() {
    throw new RuntimeException("ExceptionHandlerController - RuntimeException");
  }

  @GetMapping("/NullPointerException")
  public void nullPointerException() {
    throw new NullPointerException("ExceptionHandlerController - NullPointerException");
  }

  @GetMapping("/CustomException")
  public void customException() throws CustomException {
    throw new CustomException("ExceptionHandlerController - CusotomException");
  }

  /**
   * @ExceptionHandler({RuntimeException.class})
   *
   * <p>해당 클래스에서 RuntimeException 에러 발생 시 동작하는 예외 처리 함수
   */
  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<String> runtimeExceptionHandler(RuntimeException exception) {
    log.error("RuntimeException");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
  }

  /**
   * @ExceptionHandler({NullPointerException.class})
   *
   * <p>해당 클래스에서 NullPointerException 에러 발생 시 동작하는 예외 처리 함수
   */
  @ExceptionHandler({NullPointerException.class})
  public ResponseEntity<String> nullPointerExceptionHandler(NullPointerException exception) {
    log.error("NullPointerException");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
  }

  /**
   * @ExceptionHandler({CustomException.class})
   *
   * <p>해당 클래스에서 사용자 정의 에러인 CustomException 예외 발생 시 동작하는 예외 처리 함수
   */
  @ExceptionHandler({CustomException.class})
  public ResponseEntity<String> customExceptionHandler(CustomException exception) {
    log.error("CustomException");
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
  }
}
