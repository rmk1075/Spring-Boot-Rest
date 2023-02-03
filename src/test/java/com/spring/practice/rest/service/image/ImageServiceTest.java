package com.spring.practice.rest.service.image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.spring.practice.rest.domain.image.dto.ImageCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import com.spring.practice.rest.service.storage.StorageService;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ImageServiceTest {

    private final Long TEST_DATASET_ID = 1L;
    private final String TEST_NAME = "test.txt";
    private final String TEST_FILE_DIR = System.getProperty("user.dir") + "/resources/storage";
    private final String TEST_URL = generateTestImageUrl(TEST_DATASET_ID, TEST_NAME);
    private final byte[] TEST_FILE = "Hello World".getBytes();

    private ImageInfo testImageInfo;

    @Autowired
    ImageService imageService;

    @Autowired
    StorageService storageService;

    @BeforeEach
    void setup() throws IllegalArgumentException, URISyntaxException, IOException {
        ImageCreate imageCreate = ImageCreate.builder()
            .datasetId(TEST_DATASET_ID).name(TEST_NAME).url(TEST_URL).file(TEST_FILE).build();
        testImageInfo = imageService.createImage(imageCreate);
    }

    @AfterEach
    void tearDown() {
        try {
            imageService.getImage(testImageInfo.getId());
            imageService.deleteImage(testImageInfo.getId());
        } catch (Exception e) {}
    }

    @Test
    void testCreateImage() throws IllegalArgumentException, URISyntaxException, IOException {
        assertThrows(
            IllegalArgumentException.class,
            () -> imageService.createImage(
                ImageCreate.builder()
                    .datasetId(1L)
                    .name(TEST_NAME)
                    .url(String.format("file:///%s/%s", String.valueOf(1L), TEST_NAME))
                    .file("Hello World".getBytes()).build()
            )
        );

        Long datasetId = 1L;
        String name = "hello.txt";
        String url = generateTestImageUrl(datasetId, name);
        String contents = "Hello World";
        ImageCreate imageCreate = ImageCreate.builder()
            .datasetId(datasetId)
            .name(name)
            .url(url)
            .file(contents.getBytes()).build();

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
        ImageInfo imageInfo = imageService.deleteImage(TEST_DATASET_ID);
        assertThrows(NoSuchFileException.class, () -> storageService.get(imageInfo.getUrl()));
    }

    @Test
    void testGetImage() {
        ImageInfo imageInfo = imageService.getImage(testImageInfo.getId());
        assertEquals(imageInfo, testImageInfo);
    }

    private String generateTestImageUrl(Long datasetId, String filename) {
        return String.join("/", "file://", TEST_FILE_DIR, String.valueOf(datasetId), filename);
    }
}
