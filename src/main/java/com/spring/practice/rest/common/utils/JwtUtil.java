package com.spring.practice.rest.common.utils;

import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * JwtUtil class.
 * Provide token related features.
 */
@Component
public class JwtUtil {

  public static final String accessTokenType = "ACCESS";
  public static final String refreshTokenType = "REFRESH";
  
  private String secretKey = "secret";

  private final long accessTokenExp = 30 * 60 * 1000L; // 30분
  private final long refreshTokenExp = 6 * 30 * 60 * 1000L; // 3시간

  @Autowired UserService userService;

  public JwtUtil() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String sub, String tokenType) {
    Date now = new Date();
    Date exp = new Date(now.getTime()
        + (tokenType.equals(accessTokenType) ? accessTokenExp : refreshTokenExp));
    return Jwts.builder()
            .setHeaderParam("alg", "HS256")
            .setHeaderParam("typ", "JWT")
            .setSubject(sub)
            .setIssuedAt(now)
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
  }

  public Authentication getAuthentication(String token) {
    User user = userService.getUser(this.getUserId(token));
    return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities("ROLE_"));
  }

  public Long getUserId(String token) {
    return Long.valueOf(
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}
