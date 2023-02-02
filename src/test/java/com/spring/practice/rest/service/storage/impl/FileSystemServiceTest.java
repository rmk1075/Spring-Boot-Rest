package com.spring.practice.rest.service.storage.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class FileSystemServiceTest {

    private final String TEST_FILE_NAME = "test.txt";
    private final String CONTENT = "Hello World";
    private String TEST_FILE_PATH;

    @Autowired
    FileSystemService fileSystemService;

    @BeforeEach
    void setup() throws IOException, URISyntaxException {
        String url = String.join("/", "file://", TEST_FILE_NAME);
        String path = fileSystemService.create(url, CONTENT.getBytes());
        TEST_FILE_PATH = path;
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void testCreate() throws URISyntaxException, IOException {
        String url = "file:///temp.txt";
        byte[] bytes = fileSystemService.get(String.format(String.join("/", "file://", TEST_FILE_NAME)));
        String path = fileSystemService.create(url, bytes);

        String contents = Files.readString(Path.of(path));
        assertEquals(contents, CONTENT);

        Files.deleteIfExists(Path.of(path));
    }

    @Test
    void testDelete() throws URISyntaxException, IOException {
        String url = "file:///temp.txt";
        byte[] bytes = fileSystemService.get(String.format(String.join("/", "file://", TEST_FILE_NAME)));
        String path = fileSystemService.create(url, bytes);

        fileSystemService.delete(url);
        File file = new File(path);
        assertFalse(file.exists());
    }

    @Test
    void testGet() throws URISyntaxException, IOException {
        byte[] bytes = fileSystemService.get(String.format(String.join("/", "file://", TEST_FILE_NAME)));
        assertEquals(new String(bytes), CONTENT);
    }
}
