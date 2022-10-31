package com.spring.practice.rest.repository.impl.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.user.User;

@Repository("JpaUserInterfaceRepository")
public interface JpaUserInterfaceRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(String uid);
    Optional<User> deleteByUid(String uid);
}
