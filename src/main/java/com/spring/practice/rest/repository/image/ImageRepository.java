package com.spring.practice.rest.repository.image;

import com.spring.practice.rest.domain.image.Image;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ImageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {
  public Optional<Image> findByName(String name);

  public List<Image> findAllByDatasetId(Long datasetId);

  public List<Image> findAllByDatasetId(Long datasetId, Pageable pageable);

  public void deleteAllByDatasetId(Long dataseId);
}
