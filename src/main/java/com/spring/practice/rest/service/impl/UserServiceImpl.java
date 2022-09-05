package com.spring.practice.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;
import com.spring.practice.rest.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String id) {
        User user = userRepository.findById(id);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = this.userRepository.findAllUsers();
        return users;
    }

    @Override
    public void addUser(User user) {
        // TODO:
        userRepository.saveUser(user);
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
