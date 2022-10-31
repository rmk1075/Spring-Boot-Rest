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

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public Map<Object, Object> getUsers() {
        Map<Object, Object> map = new HashMap<>();
        List<User> users = userService.getUsers();
        map.put("users", users);
        return map;
    }
    
    @GetMapping("/{uid}")
    public Map<Object, Object> getUser(@PathVariable String uid) {
        Map<Object, Object> map = new HashMap<>();
        User user = userService.getUser(uid);
        map.put("user", user);
        return map;
    }

    @PostMapping("/{uid}")
    public void addUser(@PathVariable String uid, @RequestParam String name) {
        // id validation
        User oldUser = userService.getUser(uid);
        if(oldUser != null) throw new RuntimeException(String.format("the user is already exists. id=%s name=%s", uid, name));
        
        // add User
        User newUser = new User(uid, name);
        userService.addUser(newUser);
    }

    @PutMapping("/{uid}")
    public void updateUser(@PathVariable String uid, @RequestParam String name) {
        // id validation
        User user = userService.getUser(uid);
        if(user == null) throw new RuntimeException(String.format("the user is not exists. id=%s", uid));

        // update User
        user.setUid(uid);
        user.setName(name);
        userService.updateUser(user);

        user = userService.getUser(uid);
    }

    @DeleteMapping("/{uid}")
    public void removeUser(@PathVariable String uid) {
        // id validation
        User user = userService.getUser(uid);
        if(user == null) throw new RuntimeException(String.format("the user is not exists. id=%s", uid));

        // remove User
        userService.removeUser(user);
    }
}
