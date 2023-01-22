package com.spring.practice.rest.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.rest.service.StorageService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    StorageService storageService;

    @PostMapping("/")
    public List<String> uploadFile(@RequestPart MultipartFile[] files) throws IOException {
        List<String> result = storageService.add(files);
        return result;
    }

    @DeleteMapping("/{path}")
    public void deleteFile(@RequestParam String path) throws IOException {
        storageService.delete(path);
    }
    
}
