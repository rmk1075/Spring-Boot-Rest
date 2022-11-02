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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Map<Object, Object> getUsers() {
        Map<Object, Object> map = new HashMap<>();
        List<UserInfo> users = userService.getUsers();
        map.put("users", users);
        return map;
    }
    
    @GetMapping("/{uid}")
    public Map<Object, Object> getUser(@PathVariable String uid) {
        Map<Object, Object> map = new HashMap<>();
        UserInfo user = userService.getUser(uid);
        map.put("user", user);
        return map;
    }

    @PostMapping("/{uid}")
    public void createUser(@PathVariable String uid, @RequestParam UserCreate userCreate) {
        UserInfo created = userService.createUser(userCreate);
    }

    @PutMapping("/{uid}")
    public void updateUser(@PathVariable String uid, @RequestParam UserUpdate userUpdate) {
        UserInfo updated = userService.updateUser(uid, userUpdate);
    }

    @DeleteMapping("/{uid}")
    public void removeUser(@PathVariable String uid) {
        UserInfo removed = userService.deleteUser(uid);
    }
}
