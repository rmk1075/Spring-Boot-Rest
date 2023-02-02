package com.spring.practice.rest.service.dataset.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetCreate;
import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.repository.dataset.DatasetRepository;
import com.spring.practice.rest.service.dataset.DatasetService;
import com.spring.practice.rest.service.storage.StorageService;

@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

    private static final String SCHEME = "file";
    private static final String PATH = System.getProperty("user.dir") + "/datasets";
    
    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private CommonMapper mapper;

    @Override
    public DatasetInfo createDataset(DatasetUserCreate datasetUserCreate) throws IOException {
        String name = datasetUserCreate.getName();
        if(datasetRepository.findByName(name) != null)
            throw new IllegalArgumentException(String.format("Dataset[name=%s] is already exists.", name));

        DatasetCreate datasetCreate = DatasetCreate.builder()
            .name(name)
            .path(null)
            .size(0).build();
        DatasetInfo datasetInfo = mapper.datasetCreateToDatasetInfo(datasetCreate);
        Dataset dataset = mapper.datasetInfoToDataset(datasetInfo);
        dataset = datasetRepository.save(dataset);

        // create dataset directory
        String path = String.join("/", PATH, String.valueOf(dataset.getId()));
        Path filePath = Path.of(path);
        Files.deleteIfExists(filePath);
        Files.createDirectories(filePath);

        dataset.setPath(path);
        dataset = datasetRepository.save(dataset);
        return mapper.datasetToDatasetInfo(dataset);
    }

    @Override
    public DatasetInfo deleteDataset(Long id) {
        DatasetInfo dataset = this.getDataset(id);
        datasetRepository.delete(mapper.datasetInfoToDataset(dataset));
        return dataset;
    }

    @Override
    public DatasetInfo getDataset(Long id) {
        Optional<Dataset> dataset = datasetRepository.findById(id);
        if(!dataset.isPresent()) throw new IllegalArgumentException(String.format("Dataset[id=%d] is not exists.", id));
        return mapper.datasetToDatasetInfo(dataset.get());
    }

    @Override
    public List<DatasetInfo> getDatasets() {
        List<DatasetInfo> datasets = datasetRepository.findAll()
            .stream().map(dataset -> mapper.datasetToDatasetInfo(dataset)).collect(Collectors.toList());
        return datasets;
    }

    @Override
    public DatasetInfo uploadDataset(Long id, MultipartFile[] files) throws IllegalArgumentException, URISyntaxException, IOException {
        DatasetInfo datasetInfo = this.getDataset(id);
        int size = 0;
        for (MultipartFile multipartFile : files) {
            String name = multipartFile.getOriginalFilename();
            String url = this.generateFileUrl(datasetInfo, name);
            String path = storageService.create(url, multipartFile.getBytes());
            size++;
        }

        Dataset dataset = datasetRepository.findById(id).get();
        dataset.setSize(size);
        datasetRepository.save(dataset);
        return mapper.datasetToDatasetInfo(dataset);
    }

    private String generateFileUrl(DatasetInfo dataset, String filepath) {
        return String.format("%s://%s/%s", SCHEME, String.valueOf(dataset.getId()), filepath);
    }
}
