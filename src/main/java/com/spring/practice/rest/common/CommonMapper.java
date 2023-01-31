package com.spring.practice.rest.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetCreate;
import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserInfo;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonMapper {
    UserInfo userToUserInfo(User user);
    User userInfoToUser(UserInfo userInfo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    DatasetInfo datasetCreateToDatasetInfo(DatasetCreate datasetCreate);
    
    DatasetInfo datasetToDatasetInfo(Dataset dataset);
    Dataset datasetInfoToDataset(DatasetInfo dataset);
}
