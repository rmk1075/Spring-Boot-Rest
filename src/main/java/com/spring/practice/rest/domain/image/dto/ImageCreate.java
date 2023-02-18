package com.spring.practice.rest.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ImageCreate dto class.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCreate {

  private Long datasetId;
  private String name;
  private String url;

  private byte[] file;
}
