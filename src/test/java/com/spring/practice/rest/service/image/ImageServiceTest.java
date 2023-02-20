package com.spring.practice.rest.service.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.image.Image;
import com.spring.practice.rest.domain.image.dto.ImageCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import com.spring.practice.rest.service.storage.StorageService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * ImageService test code.
 */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ImageServiceTest {

  private final Long testDatasetId = 1L;
  private final String testName = "test.txt";
  private final String testFileDir = System.getProperty("user.dir") + "/resources/storage";
  private final String testUrl = generateTestImageUrl(testDatasetId, testName);
  private final byte[] testFile = "Hello World".getBytes();

  private ImageInfo testImageInfo;

  @Autowired ImageService imageService;

  @Autowired StorageService storageService;

  @Autowired CommonMapper mapper;

  @BeforeEach
  void setup() throws IllegalArgumentException, URISyntaxException, IOException {
    ImageCreate imageCreate =
        ImageCreate.builder()
            .datasetId(testDatasetId)
            .name(testName)
            .url(testUrl)
            .file(testFile)
            .build();
    testImageInfo = imageService.createImage(imageCreate);
  }

  @AfterEach
  void tearDown() {
    try {
      imageService.getImage(testImageInfo.getId());
      imageService.deleteImage(testImageInfo.getId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testCreateImage() throws IllegalArgumentException, URISyntaxException, IOException {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            imageService.createImage(
                ImageCreate.builder()
                    .datasetId(1L)
                    .name(testName)
                    .url(String.format("file:///%s/%s", String.valueOf(1L), testName))
                    .file("Hello World".getBytes())
                    .build()));

    Long datasetId = 1L;
    String name = "hello.txt";
    String url = generateTestImageUrl(datasetId, name);
    String contents = "Hello World";
    ImageCreate imageCreate =
        ImageCreate.builder()
            .datasetId(datasetId)
            .name(name)
            .url(url)
            .file(contents.getBytes())
            .build();

    ImageInfo imageInfo = imageService.createImage(imageCreate);
    assertEquals(imageInfo.getName(), name);
    assertEquals(imageInfo.getUrl(), url);

    byte[] bytes = storageService.get(imageInfo.getUrl());
    assertEquals(new String(bytes), contents);

    storageService.delete(imageInfo.getUrl());
    assertThrows(IOException.class, () -> storageService.get(imageInfo.getUrl()));
  }

  @Test
  void testDeleteImage() throws IllegalArgumentException, URISyntaxException, IOException {
    ImageInfo imageInfo = imageService.deleteImage(testImageInfo.getId());
    assertThrows(NoSuchFileException.class, () -> storageService.get(imageInfo.getUrl()));
  }

  @Test
  void testGetImage() {
    Image image = imageService.getImage(testImageInfo.getId());
    assertEquals(mapper.imageToImageInfo(image), testImageInfo);
  }

  private String generateTestImageUrl(Long datasetId, String filename) {
    return String.join("/", "file://", testFileDir, String.valueOf(datasetId), filename);
  }
}
