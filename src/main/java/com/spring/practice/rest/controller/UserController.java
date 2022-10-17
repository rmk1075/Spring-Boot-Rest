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

import com.spring.practice.rest.domain.User;
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
    
    @GetMapping("/{id}")
    public Map<Object, Object> getUser(@PathVariable String id) {
        Map<Object, Object> map = new HashMap<>();
        User user = userService.getUser(id);
        map.put("user", user);
        return map;
    }

    @PostMapping("/{id}")
    public void addUser(@PathVariable String id, @RequestParam String name) {
        // id validation
        User oldUser = userService.getUser(id);
        if(oldUser != null) throw new RuntimeException(String.format("the user is already exists. id=%s name=%s", id, name));
        
        // add User
        User newUser = new User(id, name);
        userService.addUser(newUser);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestParam String name) {
        // id validation
        User user = userService.getUser(id);
        if(user == null) throw new RuntimeException(String.format("the user is not exists. id=%s", id));

        // update User
        user.setUid(id);
        user.setName(name);
        userService.updateUser(user);

        user = userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable String id) {
        // id validation
        User user = userService.getUser(id);
        if(user == null) throw new RuntimeException(String.format("the user is not exists. id=%s", id));

        // remove User
        userService.removeUser(user);
    }
}
