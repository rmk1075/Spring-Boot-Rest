package com.spring.practice.rest.repository.user;

import com.spring.practice.rest.model.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User Repository jpa interface.
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUid(String uid);

  Optional<User> findByEmail(String email);

  Optional<User> findByName(String name);
}
