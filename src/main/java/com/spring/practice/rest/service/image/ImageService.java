package com.spring.practice.rest.service.image;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.model.image.Image;
import com.spring.practice.rest.model.image.dto.ImageCreate;
import com.spring.practice.rest.repository.image.ImageRepository;
import com.spring.practice.rest.service.storage.StorageService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
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
   * Get Image.
   *
   * @param id Image id.
   * @return ImageInfo.
   */
  public Image getImage(Long id) {
    Image image = imageRepository.findById(id).orElseThrow(
        () -> new NoSuchElementException(String.format("Image[id=%d] is not exists.", id))
    );
    return image;
  }

  /**
   * Get Images by dataset.
   *
   * @param datasetId Dataset id.
   * @param start pagination start
   * @param limit pagination limit
   * @return List of Images.
   */
  public List<Image> getImagesByDataset(Long datasetId, int start, int limit) {
    Pageable pageable = PageRequest.of(start, limit);
    List<Image> images = imageRepository.findAllByDatasetId(datasetId, pageable);
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
  public Image createImage(ImageCreate imageCreate)
      throws IllegalArgumentException, URISyntaxException, IOException {
    String name = imageCreate.getName();
    if (imageRepository.findByName(name).isPresent()) {
      throw new IllegalArgumentException(String.format("Image[name=%s] is already exists.", name));
    }

    storageService.create(imageCreate.getUrl(), imageCreate.getFile());

    Image image = mapper.imageCreateToImage(imageCreate);
    image = imageRepository.save(image);
    return image;
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
  public Image deleteImage(Long id)
      throws IllegalArgumentException, URISyntaxException, IOException {
    Image image = imageRepository.findById(id).orElseThrow(
        () -> new NoSuchElementException(String.format("Image[id=%d] is not exists.", id))
    );

    storageService.delete(image.getUrl());
    imageRepository.delete(image);
    return image;
  }

  /**
   * Delete Images in the dataset.
   *
   * @param datasetId Dataset id.
   * @return Deleted image list.
   * @throws IllegalArgumentException Not exists image file name.
   * @throws URISyntaxException Invalid uri.
   * @throws IOException Image file delete error.
   */
  public List<Image> deleteImagesByDataset(Long datasetId)
      throws IllegalArgumentException, URISyntaxException, IOException {
    List<Image> images = imageRepository.findAllByDatasetId(datasetId);
    for (Image image : images) {
      storageService.delete(image.getUrl());
    }
    imageRepository.deleteAllByDatasetId(datasetId);
    return images;
  }
}
