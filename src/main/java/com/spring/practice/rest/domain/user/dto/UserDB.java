package com.spring.practice.rest.domain.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDB dto class.
 * Mapping with User entity.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDB {

  private Long id;
  private String uid;
  private String password;
  private String role;
  private String name;
  private String email;
  private String desc;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
