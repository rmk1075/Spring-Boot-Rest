package com.spring.practice.rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("JpaUserRepository")
public class JpaUserRepository implements UserRepository {

    @Autowired
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAllUsers() {
        String query = String.format("SELECT u FROM %s as u", ENTITY);
        return em.createQuery(query, User.class).getResultList();
    }

    @Override
    public User findById(String id) {
        String query = String.format("SELECT u FROM %s as u WHERE ID = :id", ENTITY);
        List<User> result = em.createQuery(query, User.class).setParameter("id", id).getResultList();
        return result.size() == 0 ? null : result.get(0);
    }

    @Override
    public void updateUser(User user) {
        String query = String.format("UPDATE %s as u SET NAME=:name WHERE ID = :id", ENTITY);
        em.createQuery(query).setParameter("name", user.getName()).setParameter("id", user.getId());
    }

    @Override
    public void removeUser(String id) {
        User user = this.findById(id);
        em.remove(user);
    }
}
