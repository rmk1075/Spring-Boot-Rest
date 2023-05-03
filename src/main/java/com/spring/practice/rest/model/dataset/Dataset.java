package com.spring.practice.rest.model.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.spring.practice.rest.model.BaseEntity;

/**
 * Dataset Entity class.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "dataset")
public class Dataset extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private String name;

  private String path;

  private int size;

  @Column(updatable = false)
  private Long createdBy;
  
  private Long updatedBy;
}
