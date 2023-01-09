package com.spring.practice.rest.examples.domain.mapperEx;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dest {
    private String id;
    private String name;

    private String dest;

    private B value;

    private String dateStr;
}
