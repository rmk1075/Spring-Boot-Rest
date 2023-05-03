package com.spring.practice.rest.common.utils;

import com.spring.practice.rest.common.CommonMapper;
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

  public static final String ACCESS_TOKEN_TYPE = "ACCESS";
  public static final String REFRESH_TOKEN_TYPE = "REFRESH";
  
  private static final long ACCESS_TOKEN_EXP = 30 * 60 * 1000L; // 30분
  private static final long REFRESH_TOKEN_EXP = 6 * 30 * 60 * 1000L; // 3시간
  
  private String secretKey = "secret";


  @Autowired UserService userService;

  @Autowired CommonMapper mapper;

  public JwtUtil() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  /**
   * Create new Token.
   *
   * @param sub subject
   * @param tokenType token type: ["ACCESS", "REFRESH"]
   * @return Created JWT
   */
  public String createToken(String sub, String tokenType) {
    Date now = new Date();
    Date exp = new Date(now.getTime()
        + (tokenType.equals(ACCESS_TOKEN_TYPE) ? ACCESS_TOKEN_EXP : REFRESH_TOKEN_EXP));
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
    return new UsernamePasswordAuthenticationToken(mapper.userToUserInfo(user), "", user.getAuthorities("ROLE_"));
  }

  public Long getUserId(String token) {
    return Long.valueOf(
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  /**
   * Validate token expiration.
   *
   * @param token JWT
   * @return Whether valid or not
   */
  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    }
  }
}
