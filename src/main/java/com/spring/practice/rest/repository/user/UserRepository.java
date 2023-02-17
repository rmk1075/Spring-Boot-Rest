package com.spring.practice.rest.repository.user;

import com.spring.practice.rest.domain.user.User;
import java.util.List;

/**
 * User Repository interface.
 */
public interface UserRepository {

  public static final String TABLE = "USERS";
  public static final String ENTITY = "User";

  public User save(User user);

  public List<User> findAll();

  public User findById(Long id);

  public User findByUid(String uid);

  public User findByEmail(String email);

  public User update(User user);

  public User delete(User user);
}
