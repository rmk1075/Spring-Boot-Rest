package com.spring.practice.rest.domain.user.dto;

import javax.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserUpdate dto class.
 */
@Data
@NoArgsConstructor
public class UserPatch {

  private String name = null;

  @Email
  private String email = null;

  private String desc = null;
}
