package com.spring.practice.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import com.spring.practice.rest.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<UserInfo> getUsers() {
        List<UserInfo> users = userService.getUsers();
        return users;
    }
    
    @GetMapping("/{uid}")
    public UserInfo getUser(@PathVariable String uid) {
        UserInfo user = userService.getUser(uid);
        return user;
    }

    @PostMapping("/")
    public UserInfo createUser(@RequestBody UserCreate userCreate) {
        UserInfo created = userService.createUser(userCreate);
        return created;
    }

    @PutMapping("/{uid}")
    public UserInfo updateUser(@PathVariable String uid, @RequestBody UserUpdate userUpdate) {
        UserInfo updated = userService.updateUser(uid, userUpdate);
        return updated;
    }

    @DeleteMapping("/{uid}")
    public UserInfo removeUser(@PathVariable String uid) {
        UserInfo removed = userService.deleteUser(uid);
        return removed;
    }
}
