package com.spring.practice.rest.common.exception;

import com.spring.practice.rest.common.constant.Message;

/** InvalidLoginInfoUnauthenticatedException class. Exception for unauthenticated access with invalid login info. */
public class InvalidLoginInfoUnauthenticatedException extends UnauthenticatedException {

  public InvalidLoginInfoUnauthenticatedException() {
    super(Message.INVALID_LOGIN_INFO.getMessage());
  }
}
