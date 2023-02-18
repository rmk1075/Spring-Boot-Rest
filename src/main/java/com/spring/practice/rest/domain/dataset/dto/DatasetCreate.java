package com.spring.practice.rest.domain.dataset.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DatasetCreate dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class DatasetCreate {

  @NonNull
  @NotNull
  private String name;

  private String path = null;

  private int size = 0;
}
