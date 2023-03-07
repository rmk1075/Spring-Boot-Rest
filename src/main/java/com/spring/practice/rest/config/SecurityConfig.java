package com.spring.practice.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.practice.rest.common.filters.JwtAuthenticationFilter;
import com.spring.practice.rest.common.utils.JwtUtil;

/**
 * Security Configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired private JwtUtil jwtUtil;

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable()
      .httpBasic().disable()
      .authorizeRequests()
      // .antMatchers("/**").hasRole("ADMIN")
      .antMatchers("/users/**").hasRole("USER")
      .antMatchers("/datasets/**").hasRole("USER")
      .antMatchers("/**").permitAll()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }
}
