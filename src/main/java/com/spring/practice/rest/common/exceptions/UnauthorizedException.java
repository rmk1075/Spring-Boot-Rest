package com.spring.practice.rest.common.exceptions;

/** UnauthorizedException class. Exception for unauthorized access. */
public class UnauthorizedException extends Exception {

  public UnauthorizedException(String message) {
    super(message);
  }
}
