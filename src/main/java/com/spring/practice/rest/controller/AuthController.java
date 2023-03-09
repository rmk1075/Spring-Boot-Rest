package com.spring.practice.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.rest.common.utils.JwtUtil;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserLogin;
import com.spring.practice.rest.model.user.dto.UserToken;
import com.spring.practice.rest.service.user.UserService;

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
  public UserToken loginUser(@RequestBody UserLogin userLogin) {
    User user = userService.getUserByUid(userLogin.getUid());
    UserToken userToken = new UserToken(
      jwtUtil.createToken(user.getId().toString(), JwtUtil.accessTokenType),
      jwtUtil.createToken(user.getId().toString(), JwtUtil.refreshTokenType)
    );
    return userToken;
  }
}
