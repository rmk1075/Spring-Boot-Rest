package com.spring.practice.rest.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.repository.UserRepository;
import com.spring.practice.rest.repository.impl.jpa.JpaUserInterfaceRepository;

@Repository("JpaUserRepository")
public class JpaUserRepository implements UserRepository {

    // @Autowired
    // @Qualifier("JpaUserEmRepository")
    // private UserRepository repository;

    @Autowired
    private JpaUserInterfaceRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User findByUid(String uid) {
        Optional<User> user = repository.findByUid(uid);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public void update(User user) {
        Optional<User> u = repository.findById(user.getId());
        if(!u.isPresent()) throw new IllegalArgumentException(String.format("User is not exists. id=%s", user.getId()));
        User result = repository.save(user);
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
