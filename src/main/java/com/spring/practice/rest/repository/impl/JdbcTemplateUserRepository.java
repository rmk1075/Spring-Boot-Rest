package com.spring.practice.rest.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String sql = String.format("INSERT INTO %s VALUES (%s, %s)", TABLE, user.getId(), user.getName());
        jdbcTemplate.update(sql);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        String sql = String.format("SELECT * FROM %s", TABLE);
        List<User> users = new ArrayList<>();
        try {
            users = Arrays.asList(jdbcTemplate.queryForList(sql).stream().map(u -> new User(String.valueOf(u.get("id")), String.valueOf(u.get("name")))).toArray(User[]::new));
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
            user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name")));
        } catch(Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        String sql = String.format("UPDATE %s SET NAME=%s WHERE ID=%s", TABLE, user.getName(), user.getId());
        try {
            jdbcTemplate.execute(sql); // TODO:
        } catch(Exception e) {
            // TODO:
        }
    }

    @Override
    public void removeUser(String id) {
        String sql = String.format("DELETE FROM %s WHERE ID=%s", TABLE, id);
        try {
            jdbcTemplate.execute(sql); //TODO:
        } catch(Exception e) {
            // TODO:
        }
    }
}
