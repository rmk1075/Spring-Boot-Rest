package com.spring.practice.rest.domain.dataset.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatasetCreate {

    private String name;
    private String path;

    @Builder.Default
    private int size = 0;
}