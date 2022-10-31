package com.spring.practice.rest.service;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void trace(String msg) {
        logger.trace(msg);
    }

    public void debug(String msg) {
        logger.debug(msg);
    }

    public void info(String msg) {
        logger.info(msg);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }

    public void error(String msg) {
        logger.error(msg);
    }

    public void logging(String level, String msg) {
        try {
            Method method = this.getClass().getMethod(level, String.class);
            method.invoke(this, msg);
        } catch (Exception e) {
            e.printStackTrace();
            this.error(String.format("invalid logging arguments. level={} msg={}", level, msg));
        }
    }
}
