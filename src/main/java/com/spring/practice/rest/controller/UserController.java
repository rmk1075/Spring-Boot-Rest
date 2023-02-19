package com.spring.practice.rest.controller;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for UserService.
 */
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @Autowired private CommonMapper mapper;

  /**
   * Get Users info.
   *
   * @return list of UserInfo.
   */
  @GetMapping("/")
  public List<UserInfo> getUsers(
      @RequestParam(required = false, defaultValue = "0") int start,
      @RequestParam(required = false, defaultValue = "100") int limit) {
    List<User> users = userService.getUsers(start, limit);
    return users.stream().map(user -> mapper.userToUserInfo(user)).toList();
  }

  /**
   * Get User info.
   *
   * @param id User id.
   * @return UserInfo.
   */
  @GetMapping("/{id}")
  public UserInfo getUser(@PathVariable Long id) {
    User user = userService.getUser(id);
    return mapper.userToUserInfo(user);
  }

  /**
   * Create new User.
   *
   * @param userCreate User create arguments.
   * @return Created UserInfo.
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  public UserInfo createUser(
      @Valid @RequestBody UserCreate userCreate
  ) {
    User user = userService.createUser(userCreate);
    return mapper.userToUserInfo(user);
  }

  /**
   * Update User info.
   *
   * @param id User id.
   * @param userUpdate User update arguments.
   * @return Updated UserInfo.
   */
  @PutMapping("/{id}")
  public UserInfo updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
    User user = userService.updateUser(id, userUpdate);
    return mapper.userToUserInfo(user);
  }

  /**
   * Delete User.
   *
   * @param id User id.
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }
}
