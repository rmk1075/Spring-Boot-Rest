package com.spring.practice.rest.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class StorageServiceImplTest {

    @Autowired
    StorageServiceImpl storageService;

    private final String ROOT_PATH = System.getProperty("user.dir");
    private final String TEST_FILE_NAME = "test.txt";
    private final String TEST_FILE_PATH = String.join("/", ROOT_PATH, TEST_FILE_NAME);

    @BeforeEach
    void setup() throws IOException {
        File file = new File(TEST_FILE_PATH);
        if(!file.exists()) file.createNewFile();
        
        try (FileWriter fw = new FileWriter(file)) {
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("안녕하세요");
            bw.close();
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void testStore() throws IOException {
        String name = TEST_FILE_NAME;
        String originalFileName = TEST_FILE_NAME;
        String contentType = "text/plain";
        byte[] content = null;
        content = Files.readAllBytes(Paths.get(TEST_FILE_PATH));

        MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);
        List<String> locations = storageService.create(new MultipartFile[]{multipartFile});

        for(String location : locations) {
            System.out.println(location);
            storageService.delete(location);
        }
    }

}
