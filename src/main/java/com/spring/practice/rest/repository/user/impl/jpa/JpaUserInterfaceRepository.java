package com.spring.practice.rest.repository.user.impl.jpa;

import com.spring.practice.rest.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User Repository jpa interface.
 */
@Repository("JpaUserInterfaceRepository")
public interface JpaUserInterfaceRepository extends JpaRepository<User, Long> {
  Optional<User> findByUid(String uid);

  Optional<User> findByEmail(String email);

  Optional<User> findByName(String name);
}
