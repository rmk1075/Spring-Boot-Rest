package com.spring.practice.rest.repository.user.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.repository.user.UserRepository;
import com.spring.practice.rest.repository.user.impl.jpa.JpaUserInterfaceRepository;

@Repository("JpaUserRepository")
public class JpaUserRepository implements UserRepository {

    // @Autowired
    // private JpaUserEmRepository repository;

    @Autowired
    private JpaUserInterfaceRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User findByUid(String uid) {
        User user = repository.findByUid(uid);
        return user;
    }

    @Override
    public User update(User user) {
        Optional<User> u = repository.findById(user.getId());
        if(!u.isPresent()) throw new IllegalArgumentException(String.format("User is not exists. id=%s", user.getId()));
        User result = repository.save(user);
        return result;
    }

    @Override
    public User delete(User user) {
        repository.delete(user);
        return user;
    }
}
