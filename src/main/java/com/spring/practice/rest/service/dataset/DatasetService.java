package com.spring.practice.rest.service.dataset;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;

public interface DatasetService {
    public DatasetInfo getDataset(Long id);
    public List<DatasetInfo> getDatasets();
    public DatasetInfo createDataset(DatasetUserCreate datasetUserCreate) throws IOException;
    // public UserInfo updateUser(Long id, UserUpdate userUpdate);
    public DatasetInfo deleteDataset(Long id);
    public DatasetInfo uploadDataset(Long id, MultipartFile[] files) throws IllegalArgumentException, URISyntaxException, IOException;
}
