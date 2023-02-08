package com.spring.practice.rest.examples.domain.lombokEx.builder;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("classpath:application-test.properties")
public class BuilderExTest {

  @Test
  public void testBuilderCreate() {
    BuilderClassEx builderClassEx = BuilderClassEx.builder().build();
    System.out.println(builderClassEx.toString());

    builderClassEx =
        BuilderClassEx.builder().id(UUID.randomUUID()).name("name").desc("Hello World").build();
    System.out.println(builderClassEx.toString());

    BuilderConstructorEx builderConstructorEx = BuilderConstructorEx.builder().build();
    System.out.println(builderConstructorEx.toString());

    builderConstructorEx = BuilderConstructorEx.builder().id(UUID.randomUUID()).build();
    System.out.println(builderConstructorEx.toString());
  }
}
