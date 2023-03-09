package com.spring.practice.rest.controller;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.model.dataset.Dataset;
import com.spring.practice.rest.model.dataset.dto.DatasetInfo;
import com.spring.practice.rest.model.dataset.dto.DatasetPatch;
import com.spring.practice.rest.model.dataset.dto.DatasetUpdate;
import com.spring.practice.rest.model.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.model.image.Image;
import com.spring.practice.rest.model.image.dto.ImageInfo;
import com.spring.practice.rest.service.dataset.DatasetService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @Autowired private CommonMapper mapper;

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
    List<Dataset> datasets = datasetService.getDatasets(start, limit);
    return datasets.stream().map(dataset -> mapper.datasetToDatasetInfo(dataset)).toList();
  }

  /**
   * Get Dataset Info.
   *
   * @param id Dataset id.
   * @return DatasetInfo.
   */
  @GetMapping("/{id}")
  public DatasetInfo getDataset(@PathVariable Long id) {
    Dataset dataset = datasetService.getDataset(id);
    return mapper.datasetToDatasetInfo(dataset);
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
    Dataset dataset = datasetService.createDataset(userCreate);
    return mapper.datasetToDatasetInfo(dataset);
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
    List<Image> images = datasetService.getImages(id, start, limit);
    return images.stream().map(image -> mapper.imageToImageInfo(image)).toList();
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
  @PostMapping("/{id}/files")
  public DatasetInfo uploadImages(@PathVariable Long id, @RequestPart MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Dataset dataset = datasetService.uploadImages(id, files);
    return mapper.datasetToDatasetInfo(dataset);
  }

  /**
   * Download image zip file from dataset.
   *
   * @param id Dataset id.
   * @return ResponseEntity with Dataset zip file.
   * @throws IllegalArgumentException Unsupported url scheme in dataset directory url.
   * @throws URISyntaxException Invalid dataset directory url.
   * @throws IOException File archive IO error.
   */
  @GetMapping("/{id}/files")
  public ResponseEntity<?> downloadImages(@PathVariable Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Resource resource = datasetService.downloadImages(id);
    String contentType = "application/octet-stream";
    String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(contentType))
      .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
      .body(resource);
  }


  @PatchMapping("/{id}")
  public DatasetInfo patchDataset(@PathVariable Long id, @RequestBody DatasetPatch datasetPatch) {
    Dataset dataset = datasetService.patchDataset(id, datasetPatch);
    return mapper.datasetToDatasetInfo(dataset);
  }

  @PutMapping("/{id}")
  public DatasetInfo updateDataset(
      @PathVariable Long id,
      @RequestBody DatasetUpdate datasetUpdate
  ) {
    Dataset dataset = datasetService.updateDataset(id, datasetUpdate);
    return mapper.datasetToDatasetInfo(dataset);
  }
}
