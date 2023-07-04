package com.spring.practice.rest.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base entity class for all entities.
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(updatable = false)
  private String createdAt;

  @LastModifiedDate
  private String updatedAt;

  @PrePersist
  void onPrePersist() {
    this.createdAt = LocalDateTime.now().toString();
    this.updatedAt = LocalDateTime.now().toString();
  }

  @PreUpdate
  void onPreUpdate() {
    this.updatedAt = LocalDateTime.now().toString();
  }
}
