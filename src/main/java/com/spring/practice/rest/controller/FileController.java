package com.spring.practice.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/")
    public void uploadFile(@RequestParam MultipartFile[] files) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }
    
}
