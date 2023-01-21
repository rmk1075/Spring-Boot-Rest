package com.spring.practice.rest.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public List<String> add(MultipartFile[] files) throws IOException;
    public void delete(String path) throws IOException;
}
