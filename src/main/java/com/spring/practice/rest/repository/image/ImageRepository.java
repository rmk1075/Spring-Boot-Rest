package com.spring.practice.rest.repository.image;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.image.Image;

@Repository("ImageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {
    public Optional<Image> findByName(String name);
}
