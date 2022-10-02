package com.spring.practice.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.practice.rest.service.LogService;

@RestController
@RequestMapping("/logging")
public class LogController {
    
    @Autowired
    private LogService logService;

    @GetMapping("/")
    public Map<String, String> getMethodName(@RequestParam String level, @RequestParam String msg) {
        Map<String, String> result = new HashMap<>();
        logService.logging(level, msg);
        return result;
    }
    
}
