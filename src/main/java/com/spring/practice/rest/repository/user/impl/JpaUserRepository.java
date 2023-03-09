package com.spring.practice.rest.repository.user.impl;

import com.spring.practice.rest.model.user.User;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.repository.user.impl.jpa.JpaUserInterfaceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * User Repository class using jpa.
 */
@Repository("JpaUserRepository")
public class JpaUserRepository implements UserRepository {

  // @Autowired
  // private JpaUserEmRepository repository;

  @Autowired
  private JpaUserInterfaceRepository repository;

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public User findById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public User findByUid(String uid) {
    return repository.findByUid(uid).orElse(null);
  }

  @Override
  public User findByName(String name) {
    return repository.findByName(name).orElse(null);
  }

  @Override
  public User findByEmail(String email) {
    return repository.findByEmail(email).orElse(null);
  }

  @Override
  public User update(User user) {
    if(!repository.existsById(user.getId())) {
      throw new NoSuchElementException(String.format("User is not exists. id=%s", user.getId()));
    }
    User result = repository.save(user);
    return result;
  }

  @Override
  public User delete(User user) {
    repository.delete(user);
    return user;
  }
}
