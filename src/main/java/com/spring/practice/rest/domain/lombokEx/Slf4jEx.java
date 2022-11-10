package com.spring.practice.rest.domain.lombokEx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Slf4jEx {
    
    // private static final Logger log = LoggerFactory.getLogger(Slf4jEx.class);

    public void logging(String message) {
        log.info(message);
    }
}
