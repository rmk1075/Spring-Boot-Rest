package com.spring.practice.rest.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("MockUserRepository")
public class MockUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    public MockUserRepository() {
        for(int i = 0; i < 10; i++) {
            users.add(new User(String.valueOf(i), String.format("test-%d", i)));
        }
    }

    @Override
    public User saveUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return users;
    }
    
    @Override
    public User findById(String id) {
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)) {
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        String id = user.getId();
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)) {
                users.set(i, user);
            }
        }
    }

    @Override
    public void removeUser(User user) {
        String id = user.getId();
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)) {
                users.remove(i);
                break;
            }
        }
    }
}
