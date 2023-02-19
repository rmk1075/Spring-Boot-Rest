package com.spring.practice.rest.service.user;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * UserService interface.
 */
public interface UserService {
  public User getUser(Long id) throws NoSuchElementException;

  public List<User> getUsers(int start, int limit);

  public User createUser(UserCreate userCreate);

  public User updateUser(Long id, UserUpdate userUpdate);

  public UserInfo deleteUser(Long id);
}
