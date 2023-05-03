package com.spring.practice.rest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // registry.addResourceHandler("/swagger-ui.html**")
    //   .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
    // registry.addResourceHandler("/webjars/**")
    //   .addResourceLocations("classpath:/META-INF/resources/webjars/");

    registry.addResourceHandler("storage/**")
      .addResourceLocations("file:" + System.getProperty("user.dir") + "/resources/storage/");
  }
}
