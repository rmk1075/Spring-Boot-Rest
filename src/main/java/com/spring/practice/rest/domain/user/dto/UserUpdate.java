package com.spring.practice.rest.domain.user.dto;

import javax.validation.constraints.Email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdate {

  private String name = null;

  @Email
  private String email = null;

  private String desc = null;
}
