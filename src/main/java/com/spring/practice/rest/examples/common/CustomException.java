package com.spring.practice.rest.examples.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 해당 예외 발생 시에 HTTP 상태 코드를 SERVICE_UNAVAILABLE (503) 으로 매핑한다.
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class CustomException extends Exception {
    public CustomException(String msg) {
        super(msg);
    }
}
