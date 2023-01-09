package com.spring.practice.rest.examples.domain.mapperEx;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Source {
    private String id;
    private String name;

    private String source;

    private A value;

    private Date date;
}
