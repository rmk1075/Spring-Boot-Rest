package com.spring.practice.rest.repository.user.impl.jpa;

import com.spring.practice.rest.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("JpaUserInterfaceRepository")
public interface JpaUserInterfaceRepository extends JpaRepository<User, Long> {
  User findByUid(String uid);
}
