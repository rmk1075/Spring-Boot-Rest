package com.spring.practice.rest.config;

import com.spring.practice.rest.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Security Configuration. */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private JwtUtil jwtUtil;

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * SecurityFilterChain for Spring Security.
   *
   * @param httpSecurity HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception Spring Security Exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // csrf 는 쿠키를 이용한 공격 방식이다. 쿠키를 사용하지 않는 경우에는 disable 해도 상관없다.
        .csrf((csrf) -> csrf.disable())
        .httpBasic((httpBasic) -> httpBasic.disable())
        // setup authorization check for the request by the user role
        .authorizeHttpRequests()
        .antMatchers(HttpMethod.POST, "/users/**")
        .permitAll()
        .antMatchers("/users/**")
        .hasAnyRole("USER", "ADMIN")
        .antMatchers("/datasets/**")
        .hasAnyRole("USER", "ADMIN")
        .antMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        // session 관리 정책을 설정한다. JWT 를 사용하기 때문에 따로 세션을 생성하지 않고 STATELESS 하게 운영한다.
        .sessionManagement(
            management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Security Filter 추가
        // UsernamePasswordAuthenticationFilter 앞에 JwtAuthenticationFilter 추가
        .addFilterBefore(
            new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
        // Exception Handling related authentication
        // - jwt authentication exception -> CustomAuthenticationEntryPoint
        // - invalid authority user access -> CustomAccessDeniedHandler
        .exceptionHandling(
            handling ->
                handling
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler()));
    return httpSecurity.build();
  }
}
