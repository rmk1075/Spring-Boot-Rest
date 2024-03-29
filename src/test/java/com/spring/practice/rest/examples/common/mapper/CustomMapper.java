package com.spring.practice.rest.examples.common.mapper;

import com.spring.practice.rest.examples.domain.mapperEx.A;
import com.spring.practice.rest.examples.domain.mapperEx.B;
import com.spring.practice.rest.examples.domain.mapperEx.Dest;
import com.spring.practice.rest.examples.domain.mapperEx.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomMapper {
  @Mapping(target = "dest", source = "source.source")
  @Mapping(target = "dateStr", source = "source.date", dateFormat = "yyyy-MM-dd HH:mm:ss")
  public Dest sourceToDest(Source source);

  @Mapping(target = "source", source = "dest.dest")
  @Mapping(target = "date", source = "dest.dateStr", dateFormat = "yyyy-MM-dd HH:mm:ss")
  public Source destToSource(Dest dest);

  public B aToB(A value);

  public A bToA(B value);
}
