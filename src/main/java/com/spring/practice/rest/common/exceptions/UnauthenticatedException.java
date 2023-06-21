package com.spring.practice.rest.common.exceptions;

/** UnauthenticatedException class. Exception for unauthenticated access. */
public class UnauthenticatedException extends Exception {

  public UnauthenticatedException(String message) {
    super(message);
  }
}