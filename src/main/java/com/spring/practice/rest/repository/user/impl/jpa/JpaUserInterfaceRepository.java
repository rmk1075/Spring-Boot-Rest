package com.spring.practice.rest.repository.user.impl.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.model.user.User;

/**
 * User Repository jpa interface.
 */
@Repository("JpaUserInterfaceRepository")
public interface JpaUserInterfaceRepository extends JpaRepository<User, Long> {
  Optional<User> findByUid(String uid);

  Optional<User> findByEmail(String email);

  Optional<User> findByName(String name);
}
