package com.spring.practice.rest.model.dataset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DatasetUserCreate dto class.
 * The Dataset is created by user using api.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetUserCreate {

  private String name;

  private Long userId;
}
