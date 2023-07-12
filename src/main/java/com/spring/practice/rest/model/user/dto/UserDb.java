package com.spring.practice.rest.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDB dto class.
 * Mapping with User entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDb {

  private Long id;
  private String uid;
  private String password;
  private String role;
  private String name;
  private String email;
  private String desc;

  private String createdAt;
  private String updatedAt;
}
