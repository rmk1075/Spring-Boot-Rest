package com.spring.practice.rest.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("JdbcTemplateUserRepository")
public class JdbcTemplateUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?)", TABLE);
        try {
            jdbcTemplate.update(sql, user.getId(), user.getUid(), user.getName());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = String.format("SELECT * FROM %s", TABLE);
        List<User> users = new ArrayList<>();
        try {
            users = jdbcTemplate.query(sql, userRowMapper());
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
        return users;
    }
    
    @Override
    public User findByUid(String uid) {
        String sql = String.format("SELECT * FROM %s WHERE UID=%s", TABLE, uid);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, userRowMapper());
        } catch(Exception e) {
            if(!EmptyResultDataAccessException.class.isInstance(e)) throw new IllegalStateException(e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        String sql = String.format("UPDATE %s SET NAME=? WHERE ID=?", TABLE);
        try {
            jdbcTemplate.update(sql, user.getName(), user.getId());
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete(User user) {
        try {
            this.deleteById(user.getId());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = String.format("DELETE FROM %s WHERE ID=?", TABLE);
        try {
            jdbcTemplate.update(sql, id);
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteByUid(String uid) {
        String sql = String.format("DELETE FROM %s WHERE UID=?", TABLE);
        try {
            jdbcTemplate.update(sql, uid);
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getLong("id"), rs.getString("uid"), rs.getString("name"));
            return user;
        };
    }
}
