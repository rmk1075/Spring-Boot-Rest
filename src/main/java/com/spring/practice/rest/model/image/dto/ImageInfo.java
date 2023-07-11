package com.spring.practice.rest.model.image.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/** ImageInfo dto class. Mapping with Image entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {

  @NonNull @NotNull private Long id;

  @NonNull @NotNull private Long datasetId;

  @NonNull @NotNull private String name;

  @NonNull @NotNull private String url;

  @NonNull @NotNull private String createdAt;

  @NonNull @NotNull private String updatedAt;
}
