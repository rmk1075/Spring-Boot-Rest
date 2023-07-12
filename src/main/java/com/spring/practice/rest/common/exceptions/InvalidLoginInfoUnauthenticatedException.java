package com.spring.practice.rest.common.exceptions;

import com.spring.practice.rest.common.constants.Message;

/** InvalidLoginInfoUnauthenticatedException class. Exception for unauthenticated access with invalid login info. */
public class InvalidLoginInfoUnauthenticatedException extends UnauthenticatedException {

  public InvalidLoginInfoUnauthenticatedException() {
    super(Message.INVALID_LOGIN_INFO.getMessage());
  }
}
