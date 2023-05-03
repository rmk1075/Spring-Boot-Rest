package com.spring.practice.rest.model.dataset.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DatasetInfo dto class.
 * Mapping with Dataset entity.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetInfo {

  private Long id;
  private String name;
  private String path;
  private int size;

  private Long createdBy;
  private Long updatedBy;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
