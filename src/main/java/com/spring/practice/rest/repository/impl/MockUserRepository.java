package com.spring.practice.rest.repository.impl;

import java.sql.SQLException;
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
    public void saveUser(User user) {
        try {
            users.add(user);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        try {
            return users;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    @Override
    public User findById(String id) {
        User user = null;
        try {
            for(int i = 0; i < users.size(); i++) {
                if(users.get(i).getId().equals(id)) {
                    user = users.get(i);
                }
            }

            if(user == null) throw new SQLException(String.format("No matching id. id=%s", id));
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        try {
            String id = user.getId();
            for(int i = 0; i < users.size(); i++) {
                if(users.get(i).getId().equals(id)) {
                    users.set(i, user);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeUser(String id) {
        try {
            for(int i = 0; i < users.size(); i++) {
                if(users.get(i).getId().equals(id)) {
                    users.remove(i);
                    break;
                }
            }
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
