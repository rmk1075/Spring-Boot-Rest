package com.spring.practice.rest.model.dataset.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DatasetUpdate dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DatasetUpdate {

  @NonNull
  @NotNull
  private String name;

}
