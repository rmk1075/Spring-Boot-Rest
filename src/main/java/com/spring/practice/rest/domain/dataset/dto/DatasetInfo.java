package com.spring.practice.rest.domain.dataset.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DatasetInfo {

    private Long id;
    private String name;
    private String path;
    private int size;

    private Date created;
    private Date updated;
}
