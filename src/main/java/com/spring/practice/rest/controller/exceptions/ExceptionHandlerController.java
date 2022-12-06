package com.spring.practice.rest.controller.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @ExceptionHandler()
 * 
 * 해당 클래스에서 발생하는 error 에 처리를 위한 어노테이션
 * 어노테이션에 지정된 error 가 발생하면 어노테이션이 설정된 메서드가 실행된다.
 */
@Slf4j
@RestController
@RequestMapping("/exceptions/exception-handler")
public class ExceptionHandlerController {
    
    @GetMapping("/RuntimeException")
    public void runtimeException() {
        throw new RuntimeException("ExceptionController - RuntimeException");
    }

    @GetMapping("/NullPointerException")
    public void nullPointerException() {
        throw new NullPointerException();
    }

    /**
     * @ExceptionHandler({RuntimeException.class})
     * 
     * 해당 클래스에서 RuntimeException 에러 발생 시 동작하는 예외 처리 함수
     */
    @ExceptionHandler({RuntimeException.class})
    public void runtimeExceptionHandler() {
        log.error("RuntimeException");
    }

    /**
     * @ExceptionHandler({NullPointerException.class})
     * 
     * 해당 클래스에서 NullPointerException 에러 발생 시 동작하는 예외 처리 함수
     */
    @ExceptionHandler({NullPointerException.class})
    public void nullPointerExceptionHandler() {
        log.error("NullPointerException");
    }
}
