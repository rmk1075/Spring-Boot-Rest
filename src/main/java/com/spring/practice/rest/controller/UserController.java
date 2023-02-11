package com.spring.practice.rest.controller;

import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.service.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @GetMapping("/")
  public List<UserInfo> getUsers() {
    List<UserInfo> users = userService.getUsers();
    return users;
  }

  @GetMapping("/{id}")
  public UserInfo getUser(@PathVariable Long id) {
    UserInfo user = userService.getUser(id);
    return user;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/")
  public UserInfo createUser(@RequestBody UserCreate userCreate) {
    UserInfo created = userService.createUser(userCreate);
    return created;
  }

  @PutMapping("/{id}")
  public UserInfo updateUser(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
    UserInfo updated = userService.updateUser(id, userUpdate);
    return updated;
  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }
}
