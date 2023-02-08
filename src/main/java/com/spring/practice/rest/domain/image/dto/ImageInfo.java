package com.spring.practice.rest.domain.image.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {

    private Long id;
    private Long datasetId;
    private String name;
    private String url;

    private Date created;
    private Date updated;
}
