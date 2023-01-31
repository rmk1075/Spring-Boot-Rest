package com.spring.practice.rest.service.dataset;

import java.util.List;

import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.dataset.dto.DatasetUserCreate;

public interface DatasetService {
    public DatasetInfo getDataset(Long id);
    public List<DatasetInfo> getDatasets();
    public DatasetInfo createDataset(DatasetUserCreate datasetUserCreate);
    // public UserInfo updateUser(Long id, UserUpdate userUpdate);
    public DatasetInfo deleteDataset(Long id);
}
