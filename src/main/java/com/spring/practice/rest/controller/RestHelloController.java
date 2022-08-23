package com.spring.practice.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController
 * 
 * @RestController 는 간단하게 @Controller 와 @ResponseBody 를 더한 어노테이션이다.
 * 
 */
@RestController
@RequestMapping("/rest")
public class RestHelloController {

    @GetMapping("home")
    public String getHome() {
        return "index.html";
    }

    @GetMapping("hello")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("test")
    public Map<Object, Object> getTest() {
        Map<Object, Object> map = new HashMap<>();
        map.put("content", "hello");
        return  map;
    }
}