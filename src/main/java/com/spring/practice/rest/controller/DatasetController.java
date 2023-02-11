package com.spring.practice.rest.controller;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
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

@RestController
@RequestMapping("/datasets")
public class DatasetController {

  @Autowired private DatasetService datasetService;

  @GetMapping("/")
  public List<DatasetInfo> getDatasets(
      @RequestParam(required = false, defaultValue = "0") int start,
      @RequestParam(required = false, defaultValue = "100") int limit) {
    List<DatasetInfo> datasets = datasetService.getDatasets(start, limit);
    return datasets;
  }

  @GetMapping("/{id}")
  public DatasetInfo getDataset(@PathVariable Long id) {
    DatasetInfo dataset = datasetService.getDataset(id);
    return dataset;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  public DatasetInfo createDataset(@RequestBody DatasetUserCreate userCreate) throws IOException {
    DatasetInfo dataset = datasetService.createDataset(userCreate);
    return dataset;
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteDataset(@PathVariable Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    datasetService.deleteDataset(id);
  }

  // TODO: consider async upload
  @PostMapping("/{id}/files")
  public DatasetInfo uploadDataset(@PathVariable Long id, @RequestPart MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException {
    DatasetInfo dataset = datasetService.uploadDataset(id, files);
    return dataset;
  }
}
