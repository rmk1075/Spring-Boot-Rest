package com.spring.practice.rest.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.rest.service.StorageService;

@Service
public class StorageServiceImpl implements StorageService {

    private final String PATH = System.getProperty("user.dir") + "/output";

    StorageServiceImpl() throws IOException {
        if(!Files.exists(Path.of(PATH))) Files.createDirectories(Path.of(PATH));
    }

    @Override
    public List<String> add(MultipartFile[] files) throws IOException {
        List<String> locations = new LinkedList<>();
        for(MultipartFile file : files) {
            String name = file.getOriginalFilename();
            String path = this.generateFilePath(name);
            File newFile = new File(path);
            file.transferTo(newFile);
            locations.add(path);
        }
        return locations;
    }
    
    @Override
    public void delete(String path) throws IOException {
        Files.deleteIfExists(Path.of(path));
    }

    private String generateFilePath(String filename) {
        return String.join("/", PATH, filename);
    }
}
