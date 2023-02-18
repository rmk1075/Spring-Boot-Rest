package com.spring.practice.rest.domain.user.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserInfo dto class.
 * Mapping with User entity.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

  private Long id;
  private String uid;
  private String name;
  private String email;
  private String desc;

  private Date created;
  private Date updated;
}
