package com.spring.practice.rest.repository.user.impl.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.user.User;

@Repository("JpaUserInterfaceRepository")
public interface JpaUserInterfaceRepository extends JpaRepository<User, Long> {
    User findByUid(String uid);
}