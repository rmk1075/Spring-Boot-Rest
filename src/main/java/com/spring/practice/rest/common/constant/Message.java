package com.spring.practice.rest.common.constant;

/** Message constant. */
public enum Message {
  INVALID_LOGIN_INFO("Invalid login info."),
  INVALID_TOKEN("Invalid token."),
  USER_UNAUTHORIZED("User[id=%d] is unauthorized. User[id=%d] is expected."),
  USER_ID_NOT_FOUND("User[id=%d] is not found."),
  USER_UID_NOT_FOUND("User[uid=%s] is not found."),
  USER_INFO_DUPLICATED("User[%s] is duplicated."),
  ;

  private String message;

  Message(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
