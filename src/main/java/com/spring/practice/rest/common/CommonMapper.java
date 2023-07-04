package com.spring.practice.rest.common;

import com.spring.practice.rest.model.dataset.Dataset;
import com.spring.practice.rest.model.dataset.dto.DatasetCreate;
import com.spring.practice.rest.model.dataset.dto.DatasetInfo;
import com.spring.practice.rest.model.image.Image;
import com.spring.practice.rest.model.image.dto.ImageCreate;
import com.spring.practice.rest.model.image.dto.ImageInfo;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserDb;
import com.spring.practice.rest.model.user.dto.UserInfo;
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

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", source = "userId")
  @Mapping(target = "updatedBy", source = "userId")
  Dataset datasetCreateToDataset(DatasetCreate datasetCreate);

  DatasetInfo datasetToDatasetInfo(Dataset dataset);

  ImageInfo imageToImageInfo(Image image);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", source = "userId")
  @Mapping(target = "updatedBy", source = "userId")
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Image imageCreateToImage(ImageCreate imageCreate);
}
