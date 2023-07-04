package com.spring.practice.rest.model.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * UserToken dto class.
 */
@Data
@RequiredArgsConstructor
public class UserToken {
  
  @NonNull
  @NotNull
  private String accessToken;

  @NonNull
  @NotNull
  private String refreshToken;
}
