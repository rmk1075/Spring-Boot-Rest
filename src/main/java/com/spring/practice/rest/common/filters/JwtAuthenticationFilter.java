package com.spring.practice.rest.common.filters;

import com.spring.practice.rest.common.utils.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * JwtAuthenticationFilter.
 * Validate and filtering token from request.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtUtil jwtUtil;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String token = jwtUtil.resolveToken((HttpServletRequest) request);
    if (token != null && jwtUtil.validateToken(token)) {
      Authentication authentication = jwtUtil.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    chain.doFilter(request, response);
  }
}
