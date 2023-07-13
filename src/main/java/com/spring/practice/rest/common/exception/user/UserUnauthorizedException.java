package com.spring.practice.rest.common.exception.user;

import com.spring.practice.rest.common.constant.Message;
import com.spring.practice.rest.common.exception.base.UnauthorizedException;

/** UserUnauthorizedException class. Exception for user unauthorized access. */
public class UserUnauthorizedException extends UnauthorizedException {

  public UserUnauthorizedException(Long userId, Long expectedUserId) {
    super(String.format(Message.USER_UNAUTHORIZED.getMessage(), userId, expectedUserId));
  }
}
