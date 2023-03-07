package com.spring.practice.rest.domain.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * UserToken dto class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class UserToken {
  
  @NonNull
  @NotNull
  private String accessToken;

  @NonNull
  @NotNull
  private String refreshToken;
}
