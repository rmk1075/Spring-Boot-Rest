package com.spring.practice.rest.repository.dataset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/** DatasetRepository test code. */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class DatasetRepositoryTest {

  @Autowired DatasetRepository datasetRepository;
}
