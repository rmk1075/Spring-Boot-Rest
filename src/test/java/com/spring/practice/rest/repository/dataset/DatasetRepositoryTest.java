package com.spring.practice.rest.repository.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetCreate;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DatasetRepositoryTest {

    @Autowired
    DatasetRepository datasetRepository;

    @Autowired
    ModelMapper modelMapper;

    @Test
    void testDatasetRepository() {
        List<Dataset> datasets = datasetRepository.findAll();
        assertEquals(datasets.size(), 0);

        modelMapper.typeMap(DatasetCreate.class, Dataset.class).addMappings(
            mapper -> {
                mapper.map(obj -> null, Dataset::setId);
                mapper.map(obj -> null, Dataset::setCreated);
                mapper.map(obj -> null, Dataset::setUpdated);
            }
        );

        int length = 10;
        for(int i = 0; i < length; i++) {
            DatasetCreate datasetCreate = DatasetCreate.builder()
                .name(String.format("dataset-%d", i))
                .path(String.format("/datasets/%d", i))
                .size(0).build();
            Dataset dataset = modelMapper.map(datasetCreate, Dataset.class);
            datasetRepository.save(dataset);
        }

        datasets = datasetRepository.findAll();
        assertEquals(datasets.size(), length);
        for(Dataset dataset : datasets) {
            long id = dataset.getId();
            assertEquals(dataset.getName(), String.format("dataset-%d", id - 1));
            assertEquals(dataset.getPath(), String.format("/datasets/%d", id - 1));
            assertEquals(dataset.getSize(), 0);
        }
    }

}
