package com.spring.practice.rest.examples.common.objectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spring.practice.rest.examples.common.mapper.CustomMapper;
import com.spring.practice.rest.examples.domain.mapperEx.A;
import com.spring.practice.rest.examples.domain.mapperEx.Dest;
import com.spring.practice.rest.examples.domain.mapperEx.Source;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CustomMapperTest {

  @Autowired CustomMapper mapstructMapper;

  private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  @Test
  public void testMapstructMapper() throws ParseException {
    Source source = new Source();
    source.setId("testId");
    source.setName("testName");
    source.setSource("value");

    A value = new A();
    value.setValue("value");
    source.setValue(value);

    Date date = new Date();
    source.setDate(date);

    System.out.println(source.toString());

    Dest dest = mapstructMapper.sourceToDest(source);
    System.out.println(dest.toString());

    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    assertEquals(source.getId(), dest.getId());
    assertEquals(source.getName(), dest.getName());
    assertEquals(source.getValue().getValue(), dest.getValue().getValue());
    assertEquals(source.getDate().toString(), format.parse(dest.getDateStr()).toString());

    source = mapstructMapper.destToSource(dest);
    System.out.println(source.toString());

    assertEquals(source.getId(), dest.getId());
    assertEquals(source.getName(), dest.getName());
    assertEquals(source.getValue().getValue(), dest.getValue().getValue());
    assertEquals(source.getDate().toString(), format.parse(dest.getDateStr()).toString());
  }
}
