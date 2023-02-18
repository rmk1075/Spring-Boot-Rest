package com.spring.practice.rest.service.dataset;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * DatasetService interface.
 */
public interface DatasetService {
  public DatasetInfo getDataset(Long id);

  public List<DatasetInfo> getDatasets(int start, int limit);

  public DatasetInfo createDataset(DatasetUserCreate datasetUserCreate) throws IOException;

  // public UserInfo updateUser(Long id, UserUpdate userUpdate);

  public DatasetInfo deleteDataset(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException;

  public List<DatasetInfo> deleteAllDatasets()
      throws IllegalArgumentException, URISyntaxException, IOException;

  public DatasetInfo uploadDataset(Long id, MultipartFile[] files)
      throws IllegalArgumentException, URISyntaxException, IOException;
}
