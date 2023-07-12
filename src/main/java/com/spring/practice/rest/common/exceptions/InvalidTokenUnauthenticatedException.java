package com.spring.practice.rest.common.exceptions;

import com.spring.practice.rest.common.constants.Message;

/** InvalidTokenUnauthenticatedException class. Exception for unauthenticated access with invalid token. */
public class InvalidTokenUnauthenticatedException extends UnauthenticatedException {

  public InvalidTokenUnauthenticatedException() {
    super(Message.INVALID_TOKEN.getMessage());
  }
}
