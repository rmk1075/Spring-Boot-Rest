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
public class FileSystemAdapterTest {

  private final String TEST_FILE_NAME = "test.txt";
  private final String TEST_FILE_DIR = System.getProperty("user.dir") + "/resources/storage";
  private final String TEST_FILE_PATH = String.join("/", TEST_FILE_DIR, TEST_FILE_NAME);
  private final String CONTENT = "Hello World";

  @Autowired FileSystemAdapter fileSystemAdapter;

  @BeforeEach
  void setup() throws IOException, URISyntaxException {
    String url = String.join("/", "file://", TEST_FILE_PATH);
    fileSystemAdapter.create(url, CONTENT.getBytes());
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(Path.of(TEST_FILE_PATH));
  }

  @Test
  void testCreate() throws URISyntaxException, IOException {
    String url = generateTestFileUrl("temp.txt");
    byte[] bytes =
        fileSystemAdapter.get(String.format(String.join("/", "file://", TEST_FILE_PATH)));
    String path = fileSystemAdapter.create(url, bytes);

    String contents = Files.readString(Path.of(path));
    assertEquals(contents, CONTENT);

    Files.deleteIfExists(Path.of(path));
  }

  @Test
  void testDelete() throws URISyntaxException, IOException {
    String url = generateTestFileUrl("temp.txt");
    byte[] bytes =
        fileSystemAdapter.get(String.format(String.join("/", "file://", TEST_FILE_PATH)));
    String path = fileSystemAdapter.create(url, bytes);

    fileSystemAdapter.delete(url);
    File file = new File(path);
    assertFalse(file.exists());
  }

  @Test
  void testGet() throws URISyntaxException, IOException {
    byte[] bytes =
        fileSystemAdapter.get(String.format(String.join("/", "file://", TEST_FILE_PATH)));
    assertEquals(new String(bytes), CONTENT);
  }

  private String generateTestFileUrl(String filename) {
    return String.join("/", "file://", TEST_FILE_DIR, filename);
  }
}
