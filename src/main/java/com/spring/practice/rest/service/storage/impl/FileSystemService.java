package com.spring.practice.rest.service.storage.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@Service
public class FileSystemService {

    private static final String PATH = System.getProperty("user.dir") + "/resources/storage";

    FileSystemService() throws IOException {
        if(!Files.exists(Path.of(PATH))) Files.createDirectories(Path.of(PATH));
    }

    public byte[] get(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        Path path = this.generateFilePath(uri.getPath());
        byte[] file = Files.readAllBytes(path);
        return file;
    }

    public String create(String url, byte[] bytes) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        Path path = this.generateFilePath(uri.getPath());
        path = Files.write(path, bytes);
        return path.toString();
    }

    public void delete(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        Path path = this.generateFilePath(uri.getPath());
        Files.deleteIfExists(path);
    }

    private Path generateFilePath(String filepath) {
        return Path.of(String.join("/", PATH, filepath));
    }
}
