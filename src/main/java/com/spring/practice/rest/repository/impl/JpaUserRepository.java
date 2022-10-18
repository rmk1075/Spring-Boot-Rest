package com.spring.practice.rest.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
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

    @Override
    public User findById(String id) {
        Optional<User> user = repository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public void update(User user) {
        Optional<User> u = repository.findById(user.getId());
        if(!u.isPresent()) throw new IllegalArgumentException(String.format("User is not exists. id=%s", user.getId()));
        User result = repository.save(user);
    }

    @Override
    public void remove(String id) {
        Optional<User> u = repository.findById(id);
        if(!u.isPresent()) throw new IllegalArgumentException(String.format("User is not exists. id=%s", id));
        User old = u.get();
        repository.delete(old);
    }
}
