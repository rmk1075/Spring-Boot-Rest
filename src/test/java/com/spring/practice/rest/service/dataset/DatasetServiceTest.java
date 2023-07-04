package com.spring.practice.rest.service.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.model.dataset.Dataset;
import com.spring.practice.rest.model.dataset.dto.DatasetCreate;
import com.spring.practice.rest.model.dataset.dto.DatasetPatch;
import com.spring.practice.rest.model.image.Image;
import com.spring.practice.rest.repository.dataset.DatasetRepository;
import com.spring.practice.rest.service.storage.StorageService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

/** DatasetServie test code. */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DatasetServiceTest {

  @Autowired DatasetService datasetService;

  @Autowired DatasetRepository datasetRepository;

  @Autowired StorageService storageService;

  @Autowired CommonMapper mapper;

  private static final String NAME = "dataset";

  private static final String TEST_FILE_NAME = "test.txt";
  private static final String TEST_FILE_PATH =
      String.join("/", System.getProperty("user.dir"), TEST_FILE_NAME);
  private static final String CONTENT = "Hello World";
  private static final Long USER_ID = 1L;

  @BeforeAll
  static void setupAll() throws IOException, URISyntaxException {
    Files.write(Path.of(TEST_FILE_PATH), CONTENT.getBytes());
  }

  @AfterAll
  static void tearDownAll() throws IOException {
    Files.deleteIfExists(Path.of(TEST_FILE_PATH));
  }

  @AfterEach
  void tearDown() throws IllegalArgumentException, URISyntaxException, IOException {
    datasetService.deleteAllDatasets();
  }

  private Dataset createDataset() throws IOException {
    DatasetCreate datasetCreate = new DatasetCreate(NAME, 0L);
    Dataset dataset = datasetService.createDataset(datasetCreate);
    return dataset;
  }

  @Test
  void testCreateDataset() throws IOException {
    Dataset dataset = this.createDataset();
    assertEquals(NAME, dataset.getName());
    assertEquals(
        dataset.getPath(),
        String.join(
            "/",
            System.getProperty("user.dir") + "/resources/storage",
            String.valueOf(dataset.getId())));
    assertEquals(0, dataset.getSize());
  }

  @Test
  void testDeleteDataset() throws IOException, IllegalArgumentException, URISyntaxException {
    Dataset created = this.createDataset();
    Dataset deleted = datasetService.deleteDataset(created.getId());
    assertEquals(created.getId(), deleted.getId());
    assertEquals(created.getName(), deleted.getName());
    assertEquals(created.getPath(), deleted.getPath());
    assertEquals(created.getSize(), deleted.getSize());
  }

  @Test
  void testGetDataset() throws IOException {
    Dataset created = this.createDataset();
    Dataset dataset = datasetService.getDataset(created.getId());
    assertEquals(created.getId(), dataset.getId());
    assertEquals(created.getName(), dataset.getName());
    assertEquals(created.getPath(), dataset.getPath());
    assertEquals(created.getSize(), dataset.getSize());
  }

  @Test
  void testGetDatasets() throws IOException {
    Dataset created = this.createDataset();
    List<Dataset> datasets = datasetService.getDatasets(0, 100);
    assertEquals(1, datasets.size());

    Dataset dataset = datasets.get(0);
    assertEquals(created.getId(), dataset.getId());
    assertEquals(created.getName(), dataset.getName());
    assertEquals(created.getPath(), dataset.getPath());
    assertEquals(created.getSize(), dataset.getSize());
  }

  @Test
  void testGetImages() throws IOException, IllegalArgumentException, URISyntaxException {
    Dataset created = this.createDataset();

    MultipartFile[] files =
        new MultipartFile[] {
          new MockMultipartFile(TEST_FILE_PATH, TEST_FILE_NAME, "text/plain", CONTENT.getBytes())
        };
    Dataset dataset = datasetService.uploadImages(created.getId(), files);

    List<Image> images = datasetService.getImages(dataset.getId(), 0, 100);
    Map<String, Image> imageMap = new HashMap<>();
    for (Image image : images) {
      imageMap.put(image.getName(), image);
    }

    for (MultipartFile file : files) {
      Image imageInfo = imageMap.get(file.getOriginalFilename());
      assertNotNull(imageInfo);
      assertEquals(new String(file.getBytes()), new String(storageService.get(imageInfo.getUrl())));
    }
  }

  @Test
  void testUploadImages() throws IOException, IllegalArgumentException, URISyntaxException {
    Dataset created = this.createDataset();

    MultipartFile[] files =
        new MultipartFile[] {
          new MockMultipartFile(TEST_FILE_PATH, TEST_FILE_NAME, "text/plain", CONTENT.getBytes())
        };
    Dataset dataset = datasetService.uploadImages(created.getId(), files);
    System.out.println(dataset.toString());
    assertEquals(dataset.getSize(), files.length);

    dataset = datasetService.deleteDataset(dataset.getId());
  }

  @Test
  void testPatchDataset() throws IOException {
    Dataset created = this.createDataset();

    DatasetPatch datasetPatch = new DatasetPatch();
    datasetPatch.setName("updated");
    datasetPatch.setUserId(USER_ID);

    Dataset patched = datasetService.patchDataset(created.getId(), datasetPatch);
    created = datasetService.getDataset(created.getId());

    assertEquals(created, patched);
  }

  @Test
  void testUpdateDataset() throws IOException {
    Dataset created = this.createDataset();

    DatasetPatch datasetPatch = new DatasetPatch();
    datasetPatch.setName("updated");
    datasetPatch.setUserId(USER_ID);

    Dataset updated = datasetService.patchDataset(created.getId(), datasetPatch);
    created = datasetService.getDataset(created.getId());

    assertEquals(created, updated);
  }
}
