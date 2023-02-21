package com.spring.practice.rest.service.dataset.impl;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetCreate;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.domain.image.Image;
import com.spring.practice.rest.domain.image.dto.ImageCreate;
import com.spring.practice.rest.repository.dataset.DatasetRepository;
import com.spring.practice.rest.service.dataset.DatasetService;
import com.spring.practice.rest.service.image.ImageService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
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
  public Dataset createDataset(DatasetUserCreate datasetUserCreate) throws IOException {
    String name = datasetUserCreate.getName();
    if (datasetRepository.findByName(name) != null) {
      throw new IllegalArgumentException(
          String.format("Dataset[name=%s] is already exists.", name));
    }

    DatasetCreate datasetCreate = new DatasetCreate(name);
    Dataset dataset = mapper.datasetCreateToDataset(datasetCreate);
    dataset = datasetRepository.save(dataset);

    // create dataset directory
    String path = this.generateDatasetPath(dataset);
    Path filePath = Path.of(path);
    FileUtils.deleteDirectory(filePath.toFile());
    Files.createDirectories(filePath);

    dataset.setPath(path);
    dataset = datasetRepository.save(dataset);
    return dataset;
  }

  @Override
  public Dataset deleteDataset(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Dataset dataset = this.getDataset(id);
    deleteDatasetStorage(dataset);
    datasetRepository.delete(dataset);
    return dataset;
  }

  @Override
  public List<Dataset> deleteAllDatasets()
      throws IllegalArgumentException, URISyntaxException, IOException {
    List<Dataset> datasets = datasetRepository.findAll();
    for (Dataset dataset : datasets) {
      deleteDatasetStorage(dataset);
    }
    datasetRepository.deleteAll();
    return datasets;
  }

  private void deleteDatasetStorage(Dataset dataset)
      throws IllegalArgumentException, URISyntaxException, IOException {
    imageService.deleteImagesByDataset(dataset.getId());
    Path filePath = Path.of(dataset.getPath());
    if (Files.exists(filePath) && Files.isDirectory(filePath)) {
      FileUtils.deleteDirectory(filePath.toFile());
    }
  }

  @Override
  public Dataset getDataset(Long id) {
    Dataset dataset = datasetRepository.findById(id).orElseThrow(
      () -> new NoSuchElementException(String.format("Dataset[id=%d] is not exists.", id))
    );
    return dataset;
  }

  @Override
  public List<Dataset> getDatasets(int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    List<Dataset> datasets = datasetRepository.findAll(pageable).getContent();
    return datasets;
  }

  @Override
  public List<Image> getImages(Long id, int start, int limit) {
    List<Image> images = imageService.getImagesByDataset(id, start, limit);
    return images;
  }

  @Override
  public Dataset uploadImages(Long id, MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Dataset dataset = this.getDataset(id);
    int size = 0;
    for (MultipartFile multipartFile : files) {
      String name = multipartFile.getOriginalFilename();
      String url = this.generateFileUrl(dataset, name);
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

    dataset.setSize(size);
    dataset = datasetRepository.save(dataset);
    return dataset;
  }

  private String generateDatasetPath(Dataset dataset) {
    return String.join("/", PATH, String.valueOf(dataset.getId()));
  }

  private String generateFileUrl(Dataset dataset, String filepath) {
    return String.join("/", String.format("%s:/", SCHEME), dataset.getPath(), filepath);
  }
}
