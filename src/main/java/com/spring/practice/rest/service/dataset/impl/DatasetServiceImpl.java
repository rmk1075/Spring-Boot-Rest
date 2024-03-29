package com.spring.practice.rest.service.dataset.impl;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.model.dataset.Dataset;
import com.spring.practice.rest.model.dataset.dto.DatasetCreate;
import com.spring.practice.rest.model.dataset.dto.DatasetPatch;
import com.spring.practice.rest.model.dataset.dto.DatasetUpdate;
import com.spring.practice.rest.model.image.Image;
import com.spring.practice.rest.model.image.dto.ImageCreate;
import com.spring.practice.rest.repository.dataset.DatasetRepository;
import com.spring.practice.rest.service.dataset.DatasetService;
import com.spring.practice.rest.service.image.ImageService;
import com.spring.practice.rest.service.storage.StorageService;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** DatasetService implements class. */
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

  private static final String SCHEME = "file";
  private static final String PATH = System.getProperty("user.dir") + "/resources/storage";

  @Autowired private DatasetRepository datasetRepository;

  @Autowired private ImageService imageService;

  @Autowired private StorageService storageService;

  @Autowired private CommonMapper mapper;

  @Override
  public Dataset createDataset(DatasetCreate datasetCreate) throws IOException {
    String name = datasetCreate.getName();
    if (datasetRepository.findByName(name) != null) {
      throw new IllegalArgumentException(
          String.format("Dataset[name=%s] is already exists.", name));
    }

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
    return datasetRepository
        .findById(id)
        .orElseThrow(
            () -> new NoSuchElementException(String.format("Dataset[id=%d] is not exists.", id)));
  }

  @Override
  public List<Dataset> getDatasets(int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    return datasetRepository.findAll(pageable).getContent();
  }

  @Override
  public List<Image> getImages(Long id, int start, int limit) {
    return imageService.getImagesByDataset(id, start, limit);
  }

  @Override
  public Dataset uploadImages(Long id, MultipartFile[] files, Long userId)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Dataset dataset = this.getDataset(id);
    int size = 0;
    for (MultipartFile multipartFile : files) {
      String name = multipartFile.getOriginalFilename();
      String url = this.generateFileUrl(dataset, name);
      ImageCreate imageCreate = new ImageCreate(id, name, url, multipartFile.getBytes(), userId);
      imageService.createImage(imageCreate);
      size++;
    }

    dataset.setSize(size);
    dataset.setUpdatedBy(id);
    dataset = datasetRepository.save(dataset);
    return dataset;
  }

  @Override
  public Resource downloadImages(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Dataset dataset = this.getDataset(id);
    String url = String.join("/", String.format("%s:/", SCHEME), dataset.getPath());

    File zip = storageService.getZip(url);
    File replace = new File(String.join("/", zip.getParent(), dataset.getName() + ".zip"));
    zip.renameTo(replace);

    String zipUrl = String.join("/", String.format("%s:/", SCHEME), replace.getPath());
    return new UrlResource(zipUrl);
  }

  @Override
  public Dataset patchDataset(Long id, DatasetPatch datasetPatch) {
    Dataset dataset = this.getDataset(id);

    // name validation
    String name = datasetPatch.getName();
    if (name != null && datasetRepository.findByName(name) != null) {
      throw new IllegalArgumentException(String.format("Dataset[name=%s] is already exists", name));
    }

    if (name != null) {
      dataset.setName(name);
    }
    dataset.setUpdatedBy(datasetPatch.getUserId());

    return datasetRepository.save(dataset);
  }

  @Override
  public Dataset updateDataset(Long id, DatasetUpdate datasetUpdate) {
    Dataset dataset = this.getDataset(id);

    // name validation
    String name = datasetUpdate.getName();
    if (datasetRepository.findByName(name) != null) {
      throw new IllegalArgumentException(String.format("Dataset[name=%s] is already exists", name));
    }
    dataset.setName(name);
    dataset.setUpdatedBy(datasetUpdate.getUserId());

    return datasetRepository.save(dataset);
  }

  private String generateDatasetPath(Dataset dataset) {
    return String.join("/", PATH, String.valueOf(dataset.getId()));
  }

  private String generateFileUrl(Dataset dataset, String filepath) {
    return String.join("/", String.format("%s:/", SCHEME), dataset.getPath(), filepath);
  }
}
