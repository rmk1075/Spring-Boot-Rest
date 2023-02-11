package com.spring.practice.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spring.practice.rest.examples.service.TestService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class TestServiceTest {

  @Autowired TestService testService;

  @BeforeEach
  void setup() {
    System.out.println("setup before each test");
  }

  @AfterEach
  void tearDown() {
    System.out.println("tear down after each test");
  }

  @BeforeAll
  static void setupAll() {
    System.out.println("setup before all test");
  }

  @AfterAll
  static void tearDownAll() {
    System.out.println("tear down after all test");
  }

  @Test
  void testHello() {
    String hello = testService.helloTest();
    assertEquals("Hello Test", hello);
  }

  @Test
  void testTest() {
    assertEquals(1 + 1, 2);
  }
}
