package com.spring.practice.rest.common.exception.base;

/** ExistsException class. Exception for duplicated info, resource. */
public class DuplicatedException extends IllegalArgumentException {

  public DuplicatedException(String message) {
    super(message);
  }
}
