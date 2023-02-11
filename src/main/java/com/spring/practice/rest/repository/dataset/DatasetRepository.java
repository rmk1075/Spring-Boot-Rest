package com.spring.practice.rest.repository.dataset;

import com.spring.practice.rest.domain.dataset.Dataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("DatasetRepository")
public interface DatasetRepository extends JpaRepository<Dataset, Long> {
  public Dataset findByName(String name);
}
