package com.spring.practice.rest.examples.domain.lombokEx;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class ConstructorsEx {}

@NoArgsConstructor
@Setter
@Getter
class NoArgsConstructorEx {

  private Long id;

  // public NoArgsConstructorEx() {}
}

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class VariableArgsConstructorEx {

  @NonNull private Long id;

  private final String name;
  private String desc;

  // @RequiredArgsConstructor
  // public VariableArgsConstructorEx(@NonNull Long id, String name) {
  //     this.id = id;
  //     this.name = name;
  // }

  // @AllArgsConstructor
  // public VariableArgsConstructorEx(@NonNull Long id, String name, String desc) {
  //     this.id = id;
  //     this.name = name;
  //     this.desc = desc;
  // }
}
