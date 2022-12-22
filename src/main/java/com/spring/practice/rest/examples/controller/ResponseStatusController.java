package com.spring.practice.rest.examples.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.spring.practice.rest.examples.common.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/exceptions/response-status")
public class ResponseStatusController {

    @GetMapping("/Custom-exception")
    public void customException() throws CustomException {
        throw new CustomException("ResponseStatus Test");
    }

    @GetMapping("/Bad-Request")
    public void badRequestException() {
        try {
            throw new Exception("Bad Request");
        } catch (Exception e) {
            log.error(String.format("status code=%d", HttpStatus.BAD_REQUEST.value()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad request error");
        }
    }
}
