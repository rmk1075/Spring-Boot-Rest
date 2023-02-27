package com.spring.practice.rest.examples.domain.lombokEx;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jEx {

  // private static final Logger log = LoggerFactory.getLogger(Slf4jEx.class);

  public void logging(String message) {
    log.info(message);
  }
}
