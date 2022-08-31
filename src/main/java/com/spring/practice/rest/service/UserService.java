package com.spring.practice.rest.service;

import com.spring.practice.rest.domain.User;

public interface UserService {
    public User getUser();
    public void addUser(User user);
    public void updateUser(User user);
    public void removeUser(User user);
}
