package com.spring.practice.rest.model.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * UserLogin dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserLogin {

  @NonNull
  @NotNull
  private String uid;

  @NonNull
  @NotNull
  private String password;
}
