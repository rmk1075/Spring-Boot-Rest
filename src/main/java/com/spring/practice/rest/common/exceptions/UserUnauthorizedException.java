package com.spring.practice.rest.common.exceptions;

import com.spring.practice.rest.common.constants.Message;

/** UserUnauthorizedException class. Exception for user unauthorized access. */
public class UserUnauthorizedException extends UnauthorizedException {

  public UserUnauthorizedException(Long userId, Long expectedUserId) {
    super(String.format(Message.USER_UNAUTHORIZED.getMessage(), userId, expectedUserId));
  }
}
