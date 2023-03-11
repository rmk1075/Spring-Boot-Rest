package com.spring.practice.rest.common.constants;

import java.util.List;
import java.util.Map;

/**
 * RoleAuthority class.
 */
public class RoleAuthority {

  private RoleAuthority() {}

  private static Map<String, List<String>> roleAuthoritiesMap = Map.of(
      Role.USER.name(), List.of(Role.USER.name()),
      Role.ADMIN.name(), List.of(Role.USER.name(), Role.ADMIN.name())
  );

  public static List<String> getAuthorities(String role) {
    return roleAuthoritiesMap.get(role);
  }
}
