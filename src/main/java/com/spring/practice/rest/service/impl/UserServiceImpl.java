package com.spring.practice.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;
import com.spring.practice.rest.service.UserService;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser() {
        // TODO:
        User user = userRepository.getUser();
        return user;
    }

    @Override
    public void addUser(User user) {
        // TODO:
        userRepository.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        // TODO:
        userRepository.updateUser(user);
    }

    @Override
    public void removeUser(User user) {
        // TODO:
        userRepository.removeUser(user);
    }
    
}
