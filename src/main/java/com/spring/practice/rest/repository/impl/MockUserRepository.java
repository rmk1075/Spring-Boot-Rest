package com.spring.practice.rest.repository.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("MockUserRepository")
public class MockUserRepository implements UserRepository {

    private Map<Long, User> users = new HashMap<>();

    public MockUserRepository() {
        for(int i = 0; i < 10; i++) {
            User user = new User(String.valueOf(i), String.format("test-%d", i));
            users.put(user.getId(), user);
        }
    }

    @Override
    public void save(User user) {
        try {
            users.put(user.getId(), user);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        try {
            return new ArrayList<>(users.values());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    @Override
    public User findByUid(String uid) {
        User user = null;
        try {
            Optional<User> result = users.values().stream().filter(u -> u.getUid().equals(uid)).findFirst();
            if(result == null) throw new SQLException(String.format("No matching id. uid=%s", uid));
            else user = result.get();
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        try {
            Long id = user.getId();
            if(!users.containsKey(id)) throw new SQLException(String.format("User is not exists. %s", user));
            User old = users.put(id, user);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(User user) {
        try {
            User old = users.remove(user.getId());
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            User old = users.remove(id);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteByUid(String uid) {
        try {
            User user = this.findByUid(uid);
            User old = users.remove(user.getId());
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
