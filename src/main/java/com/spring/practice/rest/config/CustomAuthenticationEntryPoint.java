package com.spring.practice.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.practice.rest.common.exception.base.UnauthenticatedException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/** CustomAuthenticationEntryPoint class. Handle exception when user is not authenticated. */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    Exception exception = (Exception) request.getAttribute("exception");
    if (exception != null && exception instanceof UnauthenticatedException) {
      this.unAuthenticatedExceptionHandler(request, response, exception);
      return;
    }
  }

  /**
   * Handle exception when user is not authenticated.
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param err Exception
   */
  public void unAuthenticatedExceptionHandler(
      HttpServletRequest request, HttpServletResponse response, Exception err) {
    log.error("UnauthenticatedException", err);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    try {
      String json = new ObjectMapper().writeValueAsString(err.getMessage());
      response.getWriter().write(json);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
