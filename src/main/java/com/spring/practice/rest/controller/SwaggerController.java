package com.spring.practice.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for Swagger access.
 */
@Controller
@RequestMapping("/swagger")
public class SwaggerController {
  @GetMapping
  public String redirectToSwagger() {
    return "redirect:/swagger-ui/index.html";
  }
}
