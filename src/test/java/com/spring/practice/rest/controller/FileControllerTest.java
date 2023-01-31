package com.spring.practice.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class FileControllerTest {

    @Autowired
    FileController fileController;

    @Test
    void testUploadFile() throws IOException {
        assertThrows(NullPointerException.class, () -> fileController.uploadFile(null));
        
        List<String> result = fileController.uploadFile(new MultipartFile[0]);
        assertEquals(result.size(), 0);
    }

}
