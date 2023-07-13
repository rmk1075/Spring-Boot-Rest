package com.spring.practice.rest.controller;

import com.spring.practice.rest.common.exception.InvalidLoginInfoUnauthenticatedException;
import com.spring.practice.rest.common.exception.UnauthenticatedException;
import com.spring.practice.rest.common.utils.JwtUtil;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserLogin;
import com.spring.practice.rest.model.user.dto.UserToken;
import com.spring.practice.rest.service.user.UserService;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for Authentication. */
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired private JwtUtil jwtUtil;

  @Autowired private UserService userService;

  /**
   * Login User.
   *
   * @param userLogin User login info.
   * @return userToken User token (access token, refresh token)
   */
  @PostMapping("/login")
  public UserToken loginUser(@RequestBody UserLogin userLogin) throws UnauthenticatedException {
    try {
      User user = userService.getUserByUid(userLogin.getUid());
      return new UserToken(
          jwtUtil.createToken(user.getId().toString(), JwtUtil.ACCESS_TOKEN_TYPE),
          jwtUtil.createToken(user.getId().toString(), JwtUtil.REFRESH_TOKEN_TYPE));
    } catch (NoSuchElementException e) {
      throw new InvalidLoginInfoUnauthenticatedException();
    }
  }
}
