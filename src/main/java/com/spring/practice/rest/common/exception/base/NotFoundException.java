package com.spring.practice.rest.common.exception.base;

import java.util.NoSuchElementException;

/** NotFoundException class. Exception for not found. */
public class NotFoundException extends NoSuchElementException {

  public NotFoundException(String message) {
    super(message);
  }
}
