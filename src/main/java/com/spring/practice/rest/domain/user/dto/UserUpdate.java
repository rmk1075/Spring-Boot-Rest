package com.spring.practice.rest.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * UserUpdate dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserUpdate {

  @NonNull
  @NotNull
  private String name;

  @NonNull
  @NotNull
  @Email
  private String email;

  @NonNull
  @NotNull
  private String desc;
}
