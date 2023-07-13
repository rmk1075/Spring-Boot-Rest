package com.spring.practice.rest.common.exception.user;

import com.spring.practice.rest.common.constant.Message;
import com.spring.practice.rest.common.exception.base.NotFoundException;

/** UserNotFoundException class. Exception for user unauthorized access. */
public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(Long id) {
    super(String.format(Message.USER_ID_NOT_FOUND.getMessage(), id));
  }

  public UserNotFoundException(String uid) {
    super(String.format(Message.USER_UID_NOT_FOUND.getMessage(), uid));
  }
}
