package com.spring.practice.rest.examples.domain.lombokEx.builder;

import java.util.UUID;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class BuilderClassEx {

    private UUID id;
    private String name;
    private String desc;
    
}
