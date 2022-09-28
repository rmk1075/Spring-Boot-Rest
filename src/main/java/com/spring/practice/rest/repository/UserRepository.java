package com.spring.practice.rest.repository;

import java.util.List;

import com.spring.practice.rest.domain.User;

public interface UserRepository {
    public static final String TABLE = "USERS";
    public static final String ENTITY = "User";
    
    public void save(User user);
    public List<User> findAll();
    public User findById(String id);
    public void update(User user);
    public void remove(String id);
}
