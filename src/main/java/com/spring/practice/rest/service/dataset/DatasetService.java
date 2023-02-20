package com.spring.practice.rest.service.dataset;

import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * DatasetService interface.
 */
public interface DatasetService {
  public Dataset getDataset(Long id);

  public List<Dataset> getDatasets(int start, int limit);

  public DatasetInfo createDataset(DatasetUserCreate datasetUserCreate) throws IOException;

  // public UserInfo updateUser(Long id, UserUpdate userUpdate);

  public DatasetInfo deleteDataset(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException;

  public List<DatasetInfo> deleteAllDatasets()
      throws IllegalArgumentException, URISyntaxException, IOException;

  public List<ImageInfo> getImages(Long id, int start, int limit);

  public DatasetInfo uploadImages(Long id, MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException;
}
