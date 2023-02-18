package com.spring.practice.rest.controller;

import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import java.util.NoSuchElementException;
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

  /**
   * Get Users info.
   *
   * @return list of UserInfo.
   */
  @GetMapping("/")
  public List<UserInfo> getUsers(
      @RequestParam(required = false, defaultValue = "0") int start,
      @RequestParam(required = false, defaultValue = "100") int limit) {
    List<UserInfo> users = userService.getUsers(start, limit);
    return users;
  }

  /**
   * Get User info.
   *
   * @param id User id.
   * @return UserInfo.
   */
  @GetMapping("/{id}")
  public UserInfo getUser(@PathVariable Long id) {
    UserInfo user = userService.getUser(id);
    if (user == null) {
      throw new NoSuchElementException(
        String.format("User[id=%s] is not exists.", String.valueOf(id)));
    }
    return user;
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
    UserInfo created = userService.createUser(userCreate);
    return created;
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
    UserInfo updated = userService.updateUser(id, userUpdate);
    return updated;
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
