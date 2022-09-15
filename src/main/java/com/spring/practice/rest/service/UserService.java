package com.spring.practice.rest.service;

import java.util.List;

import com.spring.practice.rest.domain.User;

public interface UserService {
    public User getUser(String id);
    public List<User> getUsers();
    public void addUser(User user);
    public void updateUser(User user);
    public void removeUser(String id);
}
