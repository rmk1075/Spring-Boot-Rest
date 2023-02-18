package com.spring.practice.rest.domain.dataset.dto;

import java.util.Date;
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

  private Date created;
  private Date updated;
}
