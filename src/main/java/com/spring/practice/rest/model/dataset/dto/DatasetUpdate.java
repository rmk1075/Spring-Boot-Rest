package com.spring.practice.rest.model.dataset.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DatasetUpdate {

  @NonNull
  @NotNull
  private String name;

}
