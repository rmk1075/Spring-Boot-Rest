package com.spring.practice.rest.examples.domain.mapperEx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dest {
    private String id;
    private String name;

    private String dest;

    private B value;

    private String dateStr;

    private String unmappedDest;
}
