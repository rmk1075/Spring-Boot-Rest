package com.spring.practice.rest.controller;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import com.spring.practice.rest.service.dataset.DatasetService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for DatasetService.
 */
@RestController
@RequestMapping("/datasets")
public class DatasetController {

  @Autowired private DatasetService datasetService;

  /**
   * Get Datasets info.
   *
   * @param start pagination start
   * @param limit pagination limit
   * @return list of DatasetInfo.
   */
  @GetMapping("/")
  public List<DatasetInfo> getDatasets(
      @RequestParam(required = false, defaultValue = "0") int start,
      @RequestParam(required = false, defaultValue = "100") int limit) {
    List<DatasetInfo> datasets = datasetService.getDatasets(start, limit);
    return datasets;
  }

  /**
   * Get Dataset Info.
   *
   * @param id Dataset id.
   * @return DatasetInfo.
   */
  @GetMapping("/{id}")
  public DatasetInfo getDataset(@PathVariable Long id) {
    DatasetInfo dataset = datasetService.getDataset(id);
    return dataset;
  }

  /**
   * Create new Dataset.
   *
   * @param userCreate Dataset create arguments.
   * @return Created DatasetInfo.
   * @throws IOException Dataset directory creation error.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  public DatasetInfo createDataset(@RequestBody DatasetUserCreate userCreate) throws IOException {
    DatasetInfo dataset = datasetService.createDataset(userCreate);
    return dataset;
  }

  /**
   * Delete Dataset.
   *
   * @param id Dataset id.
   * @throws IllegalArgumentException Input uri with unsupported scheme.
   * @throws URISyntaxException Invalid uri.
   * @throws IOException Image file delete error.
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteDataset(@PathVariable Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    datasetService.deleteDataset(id);
  }

  @GetMapping("/{id}/images")
  public List<ImageInfo> getImages(
      @PathVariable Long id,
      @RequestParam(required = false, defaultValue = "0") int start,
      @RequestParam(required = false, defaultValue = "100") int limit) {
    List<ImageInfo> images = datasetService.getImages(id, start, limit);
    return images;
  }

  // TODO: consider async upload
  /**
   * Upload image to dataset.
   *
   * @param id Dataset id.
   * @param files Image files.
   * @return Uploaded DatasetInfo.
   * @throws IllegalArgumentException Duplicated image file.
   * @throws URISyntaxException Invalid image file url.
   * @throws IOException Image file create error.
   */
  @PostMapping("/{id}/images")
  public DatasetInfo uploadImages(@PathVariable Long id, @RequestPart MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException {
    DatasetInfo dataset = datasetService.uploadImages(id, files);
    return dataset;
  }
}
