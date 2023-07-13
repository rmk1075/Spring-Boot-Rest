package com.spring.practice.rest.common.constant;

import java.util.List;
import java.util.Map;

/** RoleAuthority class. */
public final class RoleAuthority {

  private RoleAuthority() {}

  /** Map of role and authorities. */
  private static Map<String, List<String>> roleAuthoritiesMap =
      Map.of(
          Role.USER.name(), List.of(Role.USER.name()),
          Role.ADMIN.name(), List.of(Role.USER.name(), Role.ADMIN.name()));

  /**
   * Get authorities for a role.
   *
   * @param role user role
   * @return authorities
   */
  public static List<String> getAuthorities(final String role) {
    return roleAuthoritiesMap.get(role);
  }
}
