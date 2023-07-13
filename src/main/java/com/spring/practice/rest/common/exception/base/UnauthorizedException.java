package com.spring.practice.rest.common.exception.base;

/** UnauthorizedException class. Exception for unauthorized access. */
public class UnauthorizedException extends Exception {

  public UnauthorizedException(String message) {
    super(message);
  }
}
