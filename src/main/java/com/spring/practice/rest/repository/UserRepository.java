package com.spring.practice.rest.repository;

import java.util.List;

import com.spring.practice.rest.domain.User;

public interface UserRepository {
    public static final String TABLE = "USERS";
    
    public User saveUser(User user);
    public List<User> findAllUsers();
    public User findById(String id);
    public User updateUser(User user);
    public User removeUser(String id);
}
