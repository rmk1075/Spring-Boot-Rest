package com.spring.practice.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    public Map<Object, Object> getUser(@RequestParam String id) {
        Map<Object, Object> map = new HashMap<>();
        User user = userService.getUser(id);
        map.put("user", user);
        return map;
    }
}
