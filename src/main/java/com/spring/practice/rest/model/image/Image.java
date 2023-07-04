package com.spring.practice.rest.model.image;

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

/** Image Entity class. */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull private Long datasetId;

  @NonNull private String name;

  @NonNull private String url;

  @Column(updatable = false)
  @NonNull
  private Long createdBy;

  @NonNull private Long updatedBy;
}
