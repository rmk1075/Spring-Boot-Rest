package com.spring.practice.rest.common;

import com.spring.practice.rest.domain.dataset.Dataset;
import com.spring.practice.rest.domain.dataset.dto.DatasetCreate;
import com.spring.practice.rest.domain.dataset.dto.DatasetInfo;
import com.spring.practice.rest.domain.image.Image;
import com.spring.practice.rest.domain.image.dto.ImageCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserDb;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Object mapper using mapstruct.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonMapper {
  UserInfo userToUserInfo(User user);

  User userDbToUser(UserDb userDb);

  // TODO: datasetCreateToDataset
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Dataset datasetCreateToDataset(DatasetCreate datasetCreate);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  DatasetInfo datasetCreateToDatasetInfo(DatasetCreate datasetCreate);

  DatasetInfo datasetToDatasetInfo(Dataset dataset);

  Dataset datasetInfoToDataset(DatasetInfo dataset);

  ImageInfo imageToImageInfo(Image image);

  Image imageInfoToImage(ImageInfo imageInfo);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Image imageCreateToImage(ImageCreate imageCreate);
}
