package com.spring.practice.rest.repository;

import java.util.List;

import com.spring.practice.rest.domain.User;

public interface UserRepository {
    public User saveUser(User user);
    public List<User> findAllUsers();
    public User findById(String id);
    public void updateUser(User user);
    public void removeUser(String id);
}
