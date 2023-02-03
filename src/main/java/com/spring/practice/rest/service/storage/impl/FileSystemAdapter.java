package com.spring.practice.rest.service.storage.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.spring.practice.rest.service.storage.StorageServiceAdapter;

@Service
public class FileSystemAdapter implements StorageServiceAdapter {

    private static final String PATH = System.getProperty("user.dir") + "/resources/storage";
    private final String scheme = "file";

    FileSystemAdapter() throws IOException {
        if(!Files.exists(Path.of(PATH))) Files.createDirectories(Path.of(PATH));
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    @Override
    public byte[] get(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        Path path = this.generateFilePath(uri.getPath());
        byte[] file = Files.readAllBytes(path);
        return file;
    }

    @Override
    public String create(String url, byte[] bytes) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        Path path = this.generateFilePath(uri.getPath());
        path = Files.write(path, bytes);
        return path.toString();
    }

    @Override
    public String delete(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        Path path = this.generateFilePath(uri.getPath());
        Files.deleteIfExists(path);
        return path.toString();
    }

    private Path generateFilePath(String filepath) {
        return Path.of(String.join("/", PATH, filepath));
    }
}
