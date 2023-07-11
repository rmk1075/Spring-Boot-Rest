package com.spring.practice.rest.model.dataset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DatasetInfo dto class. Mapping with Dataset entity. */
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

  private String createdAt;
  private String updatedAt;
}
