package com.spring.practice.rest.model.dataset;

import com.spring.practice.rest.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/** Dataset Entity class. */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "dataset")
public class Dataset extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private String name;

  private String path;
  private int size;

  @Column(updatable = false)
  @NonNull
  private Long createdBy;

  @NonNull private Long updatedBy;
}
