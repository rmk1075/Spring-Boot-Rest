package com.spring.practice.rest.examples.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.spring.practice.rest.examples.domain.mapperEx.Dest;
import com.spring.practice.rest.examples.domain.mapperEx.Source;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomMapper {
    @Mapping(target = "dest", source = "source.source")
    public Dest sourceToDest(Source source);

    @Mapping(target = "source", source = "dest.dest")
    public Source destToSource(Dest dest);
}
