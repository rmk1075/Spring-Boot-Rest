package com.spring.practice.rest.service.user;

import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import java.util.List;

/**
 * UserService interface.
 */
public interface UserService {
  public UserInfo getUser(Long id);

  public List<UserInfo> getUsers(int start, int limit);

  public UserInfo createUser(UserCreate userCreate);

  public UserInfo updateUser(Long id, UserUpdate userUpdate);

  public UserInfo deleteUser(Long id);
}
