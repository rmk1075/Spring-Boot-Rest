package com.spring.practice.rest.service.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

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

    @AfterEach
    void tearDown() {
        datasetRepository.deleteAll();
    }

    private DatasetInfo createDataset() {
        DatasetUserCreate datasetUserCreate = DatasetUserCreate.builder().name(NAME).build();
        DatasetInfo datasetInfo = datasetService.createDataset(datasetUserCreate);
        return datasetInfo;
    }

    @Test
    void testCreateDataset() {
        DatasetInfo datasetInfo = this.createDataset();
        assertEquals(datasetInfo.getName(), NAME);
        assertEquals(datasetInfo.getPath(), String.format("%s/datasets/%d", System.getProperty("user.dir"), datasetInfo.getId()));
        assertEquals(datasetInfo.getSize(), 0);
    }

    @Test
    void testDeleteDataset() {
        DatasetInfo created = this.createDataset();
        DatasetInfo deleted = datasetService.deleteDataset(created.getId());
        assertEquals(created.getId(), deleted.getId());
        assertEquals(created.getName(), deleted.getName());
        assertEquals(created.getPath(), deleted.getPath());
        assertEquals(created.getSize(), deleted.getSize());
    }

    @Test
    void testGetDataset() {
        DatasetInfo created = this.createDataset();
        DatasetInfo dataset = datasetService.getDataset(created.getId());
        assertEquals(created.getId(), dataset.getId());
        assertEquals(created.getName(), dataset.getName());
        assertEquals(created.getPath(), dataset.getPath());
        assertEquals(created.getSize(), dataset.getSize());
    }

    @Test
    void testGetDatasets() {
        DatasetInfo created = this.createDataset();
        List<DatasetInfo> datasets = datasetService.getDatasets();
        assertEquals(datasets.size(), 1);

        DatasetInfo dataset = datasets.get(0);
        assertEquals(created.getId(), dataset.getId());
        assertEquals(created.getName(), dataset.getName());
        assertEquals(created.getPath(), dataset.getPath());
        assertEquals(created.getSize(), dataset.getSize());
    }
}
