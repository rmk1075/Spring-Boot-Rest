package com.spring.practice.rest.domain.user.dto;

import javax.validation.constraints.Email;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdate {

  private String name;

  @Email
  private String email;

  private String desc;
}
