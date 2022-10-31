package com.spring.practice.rest.repository;

import java.util.List;

import com.spring.practice.rest.domain.user.User;

public interface UserRepository {
    public static final String TABLE = "USERS";
    public static final String ENTITY = "User";
    
    public void save(User user);
    public List<User> findAll();
    public User findByUid(String uid);
    public void update(User user);
    public void delete(User user);
    public void deleteById(Long id);
    public void deleteByUid(String uid);
}
