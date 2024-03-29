package com.spring.practice.rest.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * UserCreate dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserCreate {

  @NonNull
  @NotNull
  private String uid;

  @NonNull
  @NotNull
  private String password;

  @NonNull
  @NotNull
  private String name;

  @NonNull
  @NotNull
  @Email
  private String email;

  private String desc = "";
}
