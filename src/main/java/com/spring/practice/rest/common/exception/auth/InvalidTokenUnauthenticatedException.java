package com.spring.practice.rest.common.exception.auth;

import com.spring.practice.rest.common.constant.Message;
import com.spring.practice.rest.common.exception.base.UnauthenticatedException;

/** InvalidTokenUnauthenticatedException class. Exception for unauthenticated access with invalid token. */
public class InvalidTokenUnauthenticatedException extends UnauthenticatedException {

  public InvalidTokenUnauthenticatedException() {
    super(Message.INVALID_TOKEN.getMessage());
  }
}
