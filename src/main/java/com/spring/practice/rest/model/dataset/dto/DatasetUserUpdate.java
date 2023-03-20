package com.spring.practice.rest.model.dataset.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DatasetUserUpdate dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DatasetUserUpdate {

  @NonNull
  @NotNull
  private String name;
}
