package com.spring.practice.rest.common.exception.user;

import com.spring.practice.rest.common.constant.Message;
import com.spring.practice.rest.common.exception.base.DuplicatedException;

/** UserInfoDuplicatedException class. Exception for user already exists. */
public class UserInfoDuplicatedException extends DuplicatedException {

  public UserInfoDuplicatedException(String info) {
    super(String.format(Message.USER_INFO_DUPLICATED.getMessage(), info));
  }
}
