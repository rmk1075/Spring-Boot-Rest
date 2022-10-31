package com.spring.practice.rest.repository.impl.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("JpaUserEmRepository")
public class JpaUserEmRepository implements UserRepository {

    @Autowired
    private EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAll() {
        String query = String.format("SELECT u FROM %s as u", ENTITY);
        return em.createQuery(query, User.class).getResultList();
    }

    public User findById(Long id) {
        User result = em.find(User.class, id);
        return result;
    }

    @Override
    public User findByUid(String uid) {
        String query = String.format("SELECT u FROM %s as u WHERE UID = :id", ENTITY);
        List<User> result = em.createQuery(query, User.class).setParameter("id", uid).getResultList();
        return result.size() == 0 ? null : result.get(0);
    }

    @Override
    public void update(User user) {
        String query = String.format("UPDATE %s as u SET NAME=:name WHERE ID = :id", ENTITY);
        em.createQuery(query).setParameter("name", user.getName()).setParameter("id", user.getId());
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

    @Override
    public void deleteByUid(String uid) {
        User user = this.findByUid(uid);
        em.remove(user);
    }
}
