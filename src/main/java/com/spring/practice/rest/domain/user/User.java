package com.spring.practice.rest.domain.user;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * User Entity class.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String uid;

  @NonNull
  private String name;

  @NonNull
  private String email;

  @Column(name = "DESCRIPTION")
  @NonNull
  private String desc;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated;
}
