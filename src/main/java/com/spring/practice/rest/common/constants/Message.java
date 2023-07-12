package com.spring.practice.rest.common.constants;

/**
 * Message constants.
 */
public enum Message {

  INVALID_LOGIN_INFO("Invalid login info."),
  INVALID_TOKEN("Invalid token."),
  USER_UNAUTHORIZED("User[id=%d] is unauthorized. User[id=%d] is expected.");

  private String message;

  Message(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
