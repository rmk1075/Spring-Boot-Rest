package com.spring.practice.rest.repository.user.impl;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * User Repository example code.
 * Mockup class using map instead of database.
 */
@Repository("MockUserRepository")
public class MockUserRepository implements UserRepository {

  private Map<Long, User> users = new HashMap<>();

  /**
   * MockUserRepository Constructor.
   * Create dummy data.
   */
  public MockUserRepository() {
    for (int i = 0; i < 10; i++) {
      User user = new User(
          String.valueOf(i),
          String.format("test-%d", i),
          String.format("test%d@mail.com", i),
          ""
      );
      users.put(user.getId(), user);
    }
  }

  @Override
  public User save(User user) {
    try {
      users.put(user.getId(), user);
      return user;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public List<User> findAll() {
    try {
      return new ArrayList<>(users.values());
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public User findById(Long id) {
    User user = null;
    try {
      user = users.get(id);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return user;
  }

  @Override
  public User findByUid(String uid) {
    User user = null;
    try {
      Optional<User> result =
          users.values().stream().filter(u -> u.getUid().equals(uid)).findFirst();
      if (result == null) {
        throw new NoSuchElementException(String.format("No matching id. uid=%s", uid));
      } else {
        user = result.get();
      }
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return user;
  }

  @Override
  public User update(User user) {
    try {
      Long id = user.getId();
      if (!users.containsKey(id)) {
        throw new NoSuchElementException(String.format("User is not exists. %s", user));
      }
      users.put(id, user);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return user;
  }

  @Override
  public User delete(User user) {
    try {
      User old = users.remove(user.getId());
      return old;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
