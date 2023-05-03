package com.spring.practice.rest.model.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserInfo dto class.
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

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
