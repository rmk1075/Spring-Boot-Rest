package com.spring.practice.rest.repository.user.impl.jpa;

import com.spring.practice.rest.domain.user.User;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("JpaUserEmRepository")
public class JpaUserEmRepository {

  private static final String ENTITY = "User";

  @Autowired private EntityManager em;

  public User save(User user) {
    em.persist(user);
    return user;
  }

  public List<User> findAll() {
    String query = String.format("SELECT u FROM %s as u", ENTITY);
    return em.createQuery(query, User.class).getResultList();
  }

  public Optional<User> findById(Long id) {
    User result = em.find(User.class, id);
    return Optional.ofNullable(result);
  }

  public Optional<User> findByUid(String uid) {
    String query = String.format("SELECT u FROM %s as u WHERE UID = :id", ENTITY);
    List<User> result = em.createQuery(query, User.class).setParameter("id", uid).getResultList();
    return Optional.ofNullable(result.size() == 0 ? null : result.get(0));
  }

  public User update(User user) {
    String query = String.format("UPDATE %s as u SET NAME=:name WHERE ID = :id", ENTITY);
    em.createQuery(query).setParameter("name", user.getName()).setParameter("id", user.getId());
    em.refresh(user);
    return user;
  }

  public User delete(User user) {
    em.remove(user);
    return user;
  }
}
