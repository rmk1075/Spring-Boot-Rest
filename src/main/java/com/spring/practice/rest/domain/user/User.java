package com.spring.practice.rest.domain.user;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String uid;
  private String name;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated;

  public User(String uid, String name) {
    this.uid = uid;
    this.name = name;
  }

  public User(Long id, String uid, String name) {
    this.id = id;
    this.uid = uid;
    this.name = name;
  }
}
