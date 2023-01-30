package com.spring.practice.rest.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class FileControllerTest {

    @Autowired
    FileController fileController;

    @Test
    void testUploadFile() {
        assertThrows(ResponseStatusException.class, () -> fileController.uploadFile(null));
    }

}
