package com.spring.practice.rest.common.constants;

/**
 * Message constants.
 */
public enum Message {

  INVALID_LOGIN_INFO("Invalid login info.");

  private String message;

  Message(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
