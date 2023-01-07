package com.spring.practice.rest.examples.common;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.spring.practice.rest.examples.domain.mapperEx.Dest;
import com.spring.practice.rest.examples.domain.mapperEx.Source;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomMapper {
    public Dest sourceToDest(Source source);
    public Source destToSource(Dest dest);
}
