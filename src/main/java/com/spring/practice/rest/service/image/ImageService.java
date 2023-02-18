package com.spring.practice.rest.service.image;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.image.Image;
import com.spring.practice.rest.domain.image.dto.ImageCreate;
import com.spring.practice.rest.domain.image.dto.ImageInfo;
import com.spring.practice.rest.repository.image.ImageRepository;
import com.spring.practice.rest.service.storage.StorageService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * ImageService class.
 */
@Service
public class ImageService {

  @Autowired ImageRepository imageRepository;

  @Autowired StorageService storageService;

  @Autowired CommonMapper mapper;

  /**
   * Get ImageInfo.
   *
   * @param id Image id.
   * @return ImageInfo.
   */
  public ImageInfo getImage(Long id) {
    Optional<Image> image = imageRepository.findById(id);
    if (image.isEmpty()) {
      throw new NoSuchElementException(String.format("Image[id=%d] is not exists.", id));
    }
    return mapper.imageToImageInfo(image.get());
  }
  
  public List<ImageInfo> getImagesByDataset(Long datasetId, int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    List<ImageInfo> images = imageRepository
        .findAllByDatasetId(datasetId, pageable)
        .stream()
        .map(image -> mapper.imageToImageInfo(image))
        .toList();
    return images;
  }

  /**
   * Create new Image.
   *
   * @param imageCreate ImageCreate arguments.
   * @return Created ImageInfo.
   * @throws IllegalArgumentException Duplicate image file name.
   * @throws URISyntaxException Invalid uri. 
   * @throws IOException Image file create error.
   */
  public ImageInfo createImage(ImageCreate imageCreate)
      throws IllegalArgumentException, URISyntaxException, IOException {
    String name = imageCreate.getName();
    if (imageRepository.findByName(name).isPresent()) {
      throw new IllegalArgumentException(String.format("Image[name=%s] is already exists.", name));
    }

    storageService.create(imageCreate.getUrl(), imageCreate.getFile());

    Image image = mapper.imageCreateToImage(imageCreate);
    image = imageRepository.save(image);
    image = imageRepository.findById(image.getId()).get();
    return mapper.imageToImageInfo(image);
  }

  /**
   * Delete Image.
   *
   * @param id ImageInfo id.
   * @return Deleted ImageInfo.
   * @throws IllegalArgumentException Not exists image file name.
   * @throws URISyntaxException Invalid uri.
   * @throws IOException Image file delete error.
   */
  public ImageInfo deleteImage(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Optional<Image> image = imageRepository.findById(id);
    if (image.isEmpty()) {
      throw new NoSuchElementException(String.format("Image[id=%d] is not exists.", id));
    }

    ImageInfo imageInfo = mapper.imageToImageInfo(image.get());
    storageService.delete(imageInfo.getUrl());
    imageRepository.delete(image.get());
    return imageInfo;
  }

  /**
   * Delete Images contained the dataset.
   *
   * @param datasetId Dataset id.
   * @return Deleted image list.
   * @throws IllegalArgumentException Not exists image file name.
   * @throws URISyntaxException Invalid uri.
   * @throws IOException Image file delete error.
   */
  public List<ImageInfo> deleteImagesByDataset(Long datasetId)
      throws IllegalArgumentException, URISyntaxException, IOException {
    List<Image> images = imageRepository.findAllByDatasetId(datasetId);
    for (Image image : images) {
      storageService.delete(image.getUrl());
    }
    imageRepository.deleteAllByDatasetId(datasetId);
    return images.stream().map(image -> mapper.imageToImageInfo(image)).toList();
  }
}
