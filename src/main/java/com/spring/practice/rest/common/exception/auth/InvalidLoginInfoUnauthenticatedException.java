package com.spring.practice.rest.common.exception.auth;

import com.spring.practice.rest.common.constant.Message;
import com.spring.practice.rest.common.exception.base.UnauthenticatedException;

/**
 * InvalidLoginInfoUnauthenticatedException class.
 * Exception for unauthenticated access with invalid login info.
 */
public class InvalidLoginInfoUnauthenticatedException extends UnauthenticatedException {

  public InvalidLoginInfoUnauthenticatedException() {
    super(Message.INVALID_LOGIN_INFO.getMessage());
  }
}
