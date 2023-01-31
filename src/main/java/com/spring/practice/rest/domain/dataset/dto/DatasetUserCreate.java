package com.spring.practice.rest.domain.dataset.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DatasetUserCreate {

    private String name;
}