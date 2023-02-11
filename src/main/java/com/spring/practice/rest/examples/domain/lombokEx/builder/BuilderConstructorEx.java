package com.spring.practice.rest.examples.domain.lombokEx.builder;

import java.util.UUID;
import lombok.Builder;
import lombok.ToString;

@ToString
public class BuilderConstructorEx {

  private UUID id;
  private String name;
  private String desc;

  @Builder
  public BuilderConstructorEx(UUID id) {
    this.id = id;
  }
}
