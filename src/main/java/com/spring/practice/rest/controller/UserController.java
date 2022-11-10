package com.spring.practice.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Map<Object, Object> getUsers() {
        log.info("getUsers()");
        Map<Object, Object> map = new HashMap<>();
        List<UserInfo> users = userService.getUsers();
        map.put("users", users);
        return map;
    }
    
    @GetMapping("/{uid}")
    public Map<Object, Object> getUser(@PathVariable String uid) {
        log.info("getUsers() uid={}", uid);
        Map<Object, Object> map = new HashMap<>();
        UserInfo user = userService.getUser(uid);
        map.put("user", user);
        return map;
    }

    @PostMapping("/")
    public void createUser(@RequestBody UserCreate userCreate) {
        log.info("craeteUser() userCreate={}", userCreate.toString());
        UserInfo created = userService.createUser(userCreate);
    }

    @PutMapping("/{uid}")
    public void updateUser(@PathVariable String uid, @RequestBody UserUpdate userUpdate) {
        log.info("updateUser() uid={}, userUpdate={}", uid, userUpdate);
        UserInfo updated = userService.updateUser(uid, userUpdate);
    }

    @DeleteMapping("/{uid}")
    public void removeUser(@PathVariable String uid) {
        log.info("removeUser() uid={}", uid);
        UserInfo removed = userService.deleteUser(uid);
    }
}
