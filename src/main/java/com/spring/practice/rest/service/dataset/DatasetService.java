package com.spring.practice.rest.service.dataset;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.rest.model.dataset.Dataset;
import com.spring.practice.rest.model.dataset.dto.DatasetPatch;
import com.spring.practice.rest.model.dataset.dto.DatasetUpdate;
import com.spring.practice.rest.model.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.model.image.Image;

/**
 * DatasetService interface.
 */
public interface DatasetService {
  public Dataset getDataset(Long id);

  public List<Dataset> getDatasets(int start, int limit);

  public List<Image> getImages(Long id, int start, int limit);

  public Dataset createDataset(DatasetUserCreate datasetUserCreate) throws IOException;

  public Dataset patchDataset(Long id, DatasetPatch datasetPatch);

  public Dataset updateDataset(Long id, DatasetUpdate datasetUpdate);

  public Dataset deleteDataset(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException;

  public List<Dataset> deleteAllDatasets()
      throws IllegalArgumentException, URISyntaxException, IOException;

  public Dataset uploadImages(Long id, MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException;

  public Resource downloadImages(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException;
}
