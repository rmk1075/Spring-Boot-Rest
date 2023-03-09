package com.spring.practice.rest.repository.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.spring.practice.rest.model.dataset.Dataset;
import com.spring.practice.rest.model.dataset.dto.DatasetCreate;

/**
 * DatasetRepository test code.
 */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DatasetRepositoryTest {

  @Autowired DatasetRepository datasetRepository;

  @Autowired ModelMapper modelMapper;

  @Test
  void testDatasetRepository() {
    List<Dataset> datasets = datasetRepository.findAll();
    assertEquals(datasets.size(), 0);

    modelMapper
        .typeMap(DatasetCreate.class, Dataset.class)
        .addMappings(
            mapper -> {
              mapper.map(obj -> null, Dataset::setId);
              mapper.map(obj -> null, Dataset::setCreatedAt);
              mapper.map(obj -> null, Dataset::setUpdatedAt);
            });

    int length = 10;
    Map<Long, DatasetCreate> map = new HashMap<>();
    for (int i = 0; i < length; i++) {
      String name = String.format("dataset-%d", i);
      String path = String.format("/datasets/%d", i);
      DatasetCreate datasetCreate = new DatasetCreate(name, path, 0);
      Dataset dataset = modelMapper.map(datasetCreate, Dataset.class);
      dataset = datasetRepository.save(dataset);
      map.put(dataset.getId(), datasetCreate);
    }

    datasets = datasetRepository.findAll();
    assertEquals(datasets.size(), length);
    for (Dataset dataset : datasets) {
      Long id = dataset.getId();
      DatasetCreate datasetCreate = map.get(id);
      assertEquals(dataset.getName(), datasetCreate.getName());
      assertEquals(dataset.getPath(), datasetCreate.getPath());
      assertEquals(dataset.getSize(), datasetCreate.getSize());
    }
  }
}
