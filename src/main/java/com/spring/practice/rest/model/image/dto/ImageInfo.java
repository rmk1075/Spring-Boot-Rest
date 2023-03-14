package com.spring.practice.rest.model.image.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ImageInfo dto class.
 * Mapping with Image entity.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {

  private Long id;
  private Long datasetId;
  private String name;
  private String url;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}