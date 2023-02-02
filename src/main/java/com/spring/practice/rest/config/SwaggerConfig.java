package com.spring.practice.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    
    /**
     * Docket
     * 
     * Swagger 설정의 핵심이 되는 Bean
     * 
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(true) // Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404) 등의 노출 여부
                .apiInfo(apiInfo()) // Swagger UI 로 노출할 정보
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.practice.rest")) // api 스펙이 작성되어 있는 패키지 (controller)
                .paths(PathSelectors.any()) // apis 에 위치하는 API 중 특정 path 를 선택
                .build();
    }

    /**
     * Swagger UI 에 노출할 API 정보를 지정
     * 
     * @return
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot Practice Rest API Documentation")
                .description("springboot rest api practice with variable backend tech")
                .version("0.1")
                .build();
    }
}

@Controller
@RequestMapping("/swagger")
class SwaggerRedirector {
    @GetMapping
    public String redirectToSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}