package com.spring.practice.rest.model.user;

import com.spring.practice.rest.common.constants.RoleAuthority;
import com.spring.practice.rest.model.BaseEntity;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * User Entity class.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "USERS")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NonNull
  private String uid;
  
  @NonNull
  private String password;

  @NonNull
  private String role;
  
  @NonNull
  private String name;

  @NonNull
  private String email;

  @Column(name = "DESCRIPTION")
  @NonNull
  private String desc;

  /**
   * Return authority list of user.
   *
   * @param rolePrefix "ROLE_" for Spring Security authorization
   * @return Authority list
   */
  public Collection<? extends GrantedAuthority> getAuthorities(String rolePrefix) {
    return RoleAuthority
      .getAuthorities(this.role)
      .stream()
      .map(authority -> new SimpleGrantedAuthority(rolePrefix + authority))
      .toList();
  }
}
