package com.spring.practice.rest.repository.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.repository.user.UserRepository;

@Repository("JdbcTemplateUserRepository")
public class JdbcTemplateUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?)", TABLE);
        try {
            jdbcTemplate.update(sql, user.getId(), user.getUid(), user.getName());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return user;
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
    public User findById(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE ID=%s", TABLE, id);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, userRowMapper());
        } catch(Exception e) {
            if(!EmptyResultDataAccessException.class.isInstance(e)) throw new IllegalStateException(e);
        }
        return user;
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
    public User update(User user) {
        String sql = String.format("UPDATE %s SET NAME=? WHERE ID=?", TABLE);
        try {
            jdbcTemplate.update(sql, user.getName(), user.getId());
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

    @Override
    public User delete(User user) {
        try {
            User old = this.deleteById(user.getId());
            return old;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public User deleteById(Long id) {
        User old = this.findById(id);
        String sql = String.format("DELETE FROM %s WHERE ID=?", TABLE);
        try {
            jdbcTemplate.update(sql, id);
        } catch(Exception e) {
            throw new IllegalStateException(e);
        }
        return old;
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getLong("id"), rs.getString("uid"), rs.getString("name"));
            return user;
        };
    }
}
