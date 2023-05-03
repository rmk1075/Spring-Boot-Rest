package com.spring.practice.rest.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * SwaggerConfig.
 * Spring swagger doc configuration.
 */
@Configuration
public class SwaggerConfig {

  private static final String AUTHORIZATION = "Authorization";

  /**
   * Docket for swagger related configuration.
   *
   * <p>Swagger 설정의 핵심이 되는 Bean
   *
   * @return Docket
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
        .ignoredParameterTypes(AuthenticationPrincipal.class) // @AuthenticationPrincipal 을 제외
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .useDefaultResponseMessages(true) // Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404) 등의 노출 여부
        .apiInfo(apiInfo()) // Swagger UI 로 노출할 정보
        .select()
        .apis(
            RequestHandlerSelectors.basePackage(
                "com.spring.practice.rest")) // api 스펙이 작성되어 있는 패키지 (controller)
        .paths(PathSelectors.any()) // apis 에 위치하는 API 중 특정 path 를 선택
        .build();
  }

  /**
   * Swagger UI 에 노출할 API 정보를 지정.
   *
   * @return API Swagger setting
   */
  public ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("SpringBoot Practice Rest API Documentation")
        .description("springboot rest api practice with variable backend tech")
        .version("0.1")
        .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
      .securityReferences(defaultAuthList())
      .build();
  }

  private List<SecurityReference> defaultAuthList() {
    AuthorizationScope authorizationScope = new AuthorizationScope("USER", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference(AUTHORIZATION, authorizationScopes));
  }

  private ApiKey apiKey() {
    return new ApiKey(AUTHORIZATION, AUTHORIZATION, "header");
  }
}
