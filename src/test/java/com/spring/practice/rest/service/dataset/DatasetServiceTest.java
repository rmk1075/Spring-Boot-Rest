package com.spring.practice.rest.service.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.repository.dataset.DatasetRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DatasetServiceTest {

    @Autowired
    DatasetService datasetService;

    @Autowired
    DatasetRepository datasetRepository;

    private final static String NAME = "dataset";

    private static final String TEST_FILE_NAME = "test.txt";
    private static final String TEST_FILE_PATH = String.join("/", System.getProperty("user.dir"), TEST_FILE_NAME);
    private static final String CONTENT = "Hello World";

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

    private DatasetInfo createDataset() throws IOException {
        DatasetUserCreate datasetUserCreate = DatasetUserCreate.builder().name(NAME).build();
        DatasetInfo datasetInfo = datasetService.createDataset(datasetUserCreate);
        return datasetInfo;
    }

    @Test
    void testCreateDataset() throws IOException {
        DatasetInfo datasetInfo = this.createDataset();
        assertEquals(datasetInfo.getName(), NAME);
        assertEquals(
            datasetInfo.getPath(),
            String.join("/", System.getProperty("user.dir") + "/resources/storage", String.valueOf(datasetInfo.getId()))
        );
        assertEquals(datasetInfo.getSize(), 0);
    }

    @Test
    void testDeleteDataset() throws IOException, IllegalArgumentException, URISyntaxException {
        DatasetInfo created = this.createDataset();
        DatasetInfo deleted = datasetService.deleteDataset(created.getId());
        assertEquals(created.getId(), deleted.getId());
        assertEquals(created.getName(), deleted.getName());
        assertEquals(created.getPath(), deleted.getPath());
        assertEquals(created.getSize(), deleted.getSize());
    }

    @Test
    void testGetDataset() throws IOException {
        DatasetInfo created = this.createDataset();
        DatasetInfo dataset = datasetService.getDataset(created.getId());
        assertEquals(created.getId(), dataset.getId());
        assertEquals(created.getName(), dataset.getName());
        assertEquals(created.getPath(), dataset.getPath());
        assertEquals(created.getSize(), dataset.getSize());
    }

    @Test
    void testGetDatasets() throws IOException {
        DatasetInfo created = this.createDataset();
        List<DatasetInfo> datasets = datasetService.getDatasets(0, 100);
        assertEquals(datasets.size(), 1);

        DatasetInfo dataset = datasets.get(0);
        assertEquals(created.getId(), dataset.getId());
        assertEquals(created.getName(), dataset.getName());
        assertEquals(created.getPath(), dataset.getPath());
        assertEquals(created.getSize(), dataset.getSize());
    }

    @Test
    void testUploadDataset() throws IOException, IllegalArgumentException, URISyntaxException {
        DatasetInfo created = this.createDataset();

        MultipartFile[] files = new MultipartFile[]{new MockMultipartFile(TEST_FILE_PATH, TEST_FILE_NAME, "text/plain", CONTENT.getBytes())};
        DatasetInfo dataset = datasetService.uploadDataset(created.getId(), files);
        System.out.println(dataset.toString());
        assertEquals(dataset.getSize(), files.length);

        dataset = datasetService.deleteDataset(dataset.getId());
    }
}
