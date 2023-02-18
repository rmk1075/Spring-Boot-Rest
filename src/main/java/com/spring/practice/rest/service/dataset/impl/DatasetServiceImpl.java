package com.spring.practice.rest.service.dataset.impl;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetCreate;
import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.domain.image.dto.ImageCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import com.spring.practice.rest.repository.dataset.DatasetRepository;
import com.spring.practice.rest.service.dataset.DatasetService;
import com.spring.practice.rest.service.image.ImageService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * DatasetService implements class.
 */
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

  private static final String SCHEME = "file";
  private static final String PATH = System.getProperty("user.dir") + "/resources/storage";

  @Autowired private DatasetRepository datasetRepository;

  @Autowired private ImageService imageService;

  @Autowired private CommonMapper mapper;

  @Override
  public DatasetInfo createDataset(DatasetUserCreate datasetUserCreate) throws IOException {
    String name = datasetUserCreate.getName();
    if (datasetRepository.findByName(name) != null) {
      throw new IllegalArgumentException(
          String.format("Dataset[name=%s] is already exists.", name));
    }

    DatasetCreate datasetCreate = new DatasetCreate(name);
    DatasetInfo datasetInfo = mapper.datasetCreateToDatasetInfo(datasetCreate);
    Dataset dataset = mapper.datasetInfoToDataset(datasetInfo);
    dataset = datasetRepository.save(dataset);
    datasetInfo = mapper.datasetToDatasetInfo(dataset);

    // create dataset directory
    String path = this.generateDatasetPath(datasetInfo);
    Path filePath = Path.of(path);
    FileUtils.deleteDirectory(filePath.toFile());
    Files.createDirectories(filePath);

    dataset.setPath(path);
    dataset = datasetRepository.save(dataset);
    return mapper.datasetToDatasetInfo(dataset);
  }

  @Override
  public DatasetInfo deleteDataset(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    DatasetInfo dataset = this.getDataset(id);
    deleteDatasetStorage(dataset);
    datasetRepository.delete(mapper.datasetInfoToDataset(dataset));
    return dataset;
  }

  @Override
  public List<DatasetInfo> deleteAllDatasets()
      throws IllegalArgumentException, URISyntaxException, IOException {
    List<Dataset> datasets = datasetRepository.findAll();
    List<DatasetInfo> datasetInfos =
        datasets.stream().map(dataset -> mapper.datasetToDatasetInfo(dataset)).toList();
    for (DatasetInfo datasetInfo : datasetInfos) {
      deleteDatasetStorage(datasetInfo);
    }
    datasetRepository.deleteAll();
    return datasetInfos;
  }

  private void deleteDatasetStorage(DatasetInfo dataset)
      throws IllegalArgumentException, URISyntaxException, IOException {
    imageService.deleteImagesByDataset(dataset.getId());
    Path filePath = Path.of(dataset.getPath());
    if (Files.exists(filePath) && Files.isDirectory(filePath)) {
      FileUtils.deleteDirectory(filePath.toFile());
    }
  }

  @Override
  public DatasetInfo getDataset(Long id) {
    Optional<Dataset> dataset = datasetRepository.findById(id);
    if (!dataset.isPresent()) {
      throw new NoSuchElementException(String.format("Dataset[id=%d] is not exists.", id));
    }
    return mapper.datasetToDatasetInfo(dataset.get());
  }

  @Override
  public List<DatasetInfo> getDatasets(int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    List<DatasetInfo> datasets =
        datasetRepository
            .findAll(pageable)
            .map(dataset -> mapper.datasetToDatasetInfo(dataset))
            .getContent();
    return datasets;
  }

  @Override
  public List<ImageInfo> getImages(Long id, int start, int limit) {
    List<ImageInfo> images = imageService.getImagesByDataset(id, start, limit);
    return images;
  }

  @Override
  public DatasetInfo uploadImages(Long id, MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException {
    DatasetInfo datasetInfo = this.getDataset(id);
    int size = 0;
    for (MultipartFile multipartFile : files) {
      String name = multipartFile.getOriginalFilename();
      String url = this.generateFileUrl(datasetInfo, name);
      ImageCreate imageCreate =
          ImageCreate.builder()
              .datasetId(id)
              .name(name)
              .url(url)
              .file(multipartFile.getBytes())
              .build();
      imageService.createImage(imageCreate);
      size++;
    }

    Dataset dataset = datasetRepository.findById(id).get();
    dataset.setSize(size);
    datasetRepository.save(dataset);
    return mapper.datasetToDatasetInfo(dataset);
  }

  private String generateDatasetPath(DatasetInfo dataset) {
    return String.join("/", PATH, String.valueOf(dataset.getId()));
  }

  private String generateFileUrl(DatasetInfo dataset, String filepath) {
    return String.join("/", String.format("%s:/", SCHEME), dataset.getPath(), filepath);
  }
}
