package com.spring.practice.rest.controller;

import com.spring.practice.rest.common.CommonMapper;
import com.spring.practice.rest.common.constants.Role;
import com.spring.practice.rest.common.exception.UnauthorizedException;
import com.spring.practice.rest.common.exception.UserUnauthorizedException;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserCreate;
import com.spring.practice.rest.model.user.dto.UserInfo;
import com.spring.practice.rest.model.user.dto.UserPatch;
import com.spring.practice.rest.model.user.dto.UserUpdate;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Controller for UserService. */
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
   * @throws UnauthorizedException User is not authorized.
   */
  @GetMapping("/{id}")
  public UserInfo getUser(
      @PathVariable Long id,
      @AuthenticationPrincipal UserInfo userInfo
  ) throws UnauthorizedException {
    User user = this.getAuthorizedUser(id, userInfo);
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
  public UserInfo createUser(@Valid @RequestBody UserCreate userCreate) {
    User user = userService.createUser(userCreate);
    return mapper.userToUserInfo(user);
  }

  /**
   * Patch User info.
   *
   * @param id User id.
   * @param userInfo User info.
   * @param userPatch User patch arguments.
   * @return Patched UserInfo.
   * @throws UnauthorizedException User is not authorized.
   */
  @PatchMapping("/{id}")
  public UserInfo patchUser(
      @PathVariable Long id,
      @AuthenticationPrincipal UserInfo userInfo,
      @RequestBody UserPatch userPatch
  ) throws UnauthorizedException {
    User user = this.getAuthorizedUser(id, userInfo);
    user = userService.patchUser(user.getId(), userPatch);
    return mapper.userToUserInfo(user);
  }

  /**
   * Update User info.
   *
   * @param id User id.
   * @param userUpdate User update arguments.
   * @return Updated UserInfo.
   * @throws UnauthorizedException User is not authorized.
   */
  @PutMapping("/{id}")
  public UserInfo updateUser(
      @PathVariable Long id,
      @AuthenticationPrincipal UserInfo userInfo,
      @Valid @RequestBody UserUpdate userUpdate
  ) throws UnauthorizedException {
    User user = this.getAuthorizedUser(id, userInfo);
    user = userService.updateUser(user.getId(), userUpdate);
    return mapper.userToUserInfo(user);
  }

  /**
   * Delete User.
   *
   * @param id User id.
   * @throws UnauthorizedException User is not authorized.
   */
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteUser(
      @PathVariable Long id,
      @AuthenticationPrincipal UserInfo userInfo
  ) throws UnauthorizedException {
    User user = this.getAuthorizedUser(id, userInfo);
    userService.deleteUser(user.getId());
  }

  /**
   * Get authorized User info.
   *
   * @param id User id.
   * @param userInfo User info.
   * @return User.
   * @throws UnauthorizedException User is not authorized.
   */
  private User getAuthorizedUser(Long id, UserInfo userInfo) throws UnauthorizedException {
    User user = userService.getUser(userInfo.getId());
    if (!user.getRole().equals(Role.ADMIN.name())) {
      if (!userInfo.getId().equals(id)) {
        throw new UserUnauthorizedException(userInfo.getId(), id);
      }
    }
    return userService.getUser(id);
  }
}
