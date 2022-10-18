package com.spring.practice.rest.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("JpaUserRepository")
public class JpaUserRepository implements UserRepository {

    @Autowired
    @Qualifier("JpaUserEmRepository")
    private UserRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByUid(String uid) {
        return repository.findByUid(uid);
    }

    @Override
    public void update(User user) {
        repository.update(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public void deleteByUid(String uid) {
        repository.deleteByUid(uid);
    }
}
