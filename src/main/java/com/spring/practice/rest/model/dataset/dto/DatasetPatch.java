package com.spring.practice.rest.model.dataset.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DatasetPatch dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DatasetPatch {

  private String name = null;

  @NonNull
  @NotNull
  private Long userId;
}
