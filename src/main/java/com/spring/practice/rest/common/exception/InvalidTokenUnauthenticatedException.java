package com.spring.practice.rest.common.exception;

import com.spring.practice.rest.common.constant.Message;

/** InvalidTokenUnauthenticatedException class. Exception for unauthenticated access with invalid token. */
public class InvalidTokenUnauthenticatedException extends UnauthenticatedException {

  public InvalidTokenUnauthenticatedException() {
    super(Message.INVALID_TOKEN.getMessage());
  }
}
