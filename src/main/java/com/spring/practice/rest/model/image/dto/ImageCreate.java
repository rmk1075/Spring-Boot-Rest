package com.spring.practice.rest.model.image.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/** ImageCreate dto class. */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ImageCreate {

  @NonNull @NotNull private Long datasetId;

  @NonNull @NotNull private String name;

  @NonNull @NotNull private String url;

  @NonNull @NotNull private byte[] file;

  @NonNull @NotNull private Long userId;
}
