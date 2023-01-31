package com.spring.practice.rest.domain.dataset.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DatasetCreate {

    private String name;
    private String path;

    @Builder.Default
    private int size = 0;
}