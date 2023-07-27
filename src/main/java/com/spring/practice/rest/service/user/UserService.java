package com.spring.practice.rest.service.user;

import com.spring.practice.rest.common.exception.user.UserInfoDuplicatedException;
import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.model.user.dto.UserCreate;
import com.spring.practice.rest.model.user.dto.UserPatch;
import com.spring.practice.rest.model.user.dto.UserUpdate;
import java.util.List;
import java.util.NoSuchElementException;

/** UserService interface. */
public interface UserService {
  public User getUser(Long id) throws NoSuchElementException;

  public User getUserByUid(String uid) throws NoSuchElementException;

  public List<User> getUsers(int start, int limit);

  public User createUser(UserCreate userCreate) throws UserInfoDuplicatedException;

  public User patchUser(Long id, UserPatch userPatch) throws UserInfoDuplicatedException;

  public User updateUser(Long id, UserUpdate userUpdate);

  public User deleteUser(Long id);
}
