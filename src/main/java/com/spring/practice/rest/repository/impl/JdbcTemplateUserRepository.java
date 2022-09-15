package com.spring.practice.rest.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("JdbcTemplateUserRepository")
public class JdbcTemplateUserRepository implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String TABLE = "USERS";

    @Override
    public User saveUser(User user) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?)", TABLE);
        jdbcTemplate.update(sql, user.getId(), user.getName());
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = String.format("SELECT * FROM %s", TABLE);
        List<User> users = new ArrayList<>();
        try {
            users = jdbcTemplate.query(sql, userRowMapper());
        } catch(Exception e) {
            return null;
        }
        return users;
    }
    
    @Override
    public User findById(String id) {
        String sql = String.format("SELECT * FROM %s WHERE ID=%s", TABLE, id);
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, userRowMapper());
        } catch(Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = String.format("UPDATE %s SET NAME=? WHERE ID=?", TABLE);
        try {
            jdbcTemplate.update(sql, user.getName(), user.getId());
        } catch(Exception e) {
            // TODO:
        }
    }

    @Override
    public void removeUser(String id) {
        String sql = String.format("DELETE FROM %s WHERE ID=?", TABLE);
        try {
            jdbcTemplate.update(sql, id);
        } catch(Exception e) {
            // TODO:
        }
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User(rs.getString("id"), rs.getString("name"));
            return user;
        };
    }
}
