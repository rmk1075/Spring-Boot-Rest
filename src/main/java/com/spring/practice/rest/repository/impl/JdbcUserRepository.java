package com.spring.practice.rest.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.spring.practice.rest.domain.User;
import com.spring.practice.rest.repository.UserRepository;

@Repository("JdbcUserRepository")
public class JdbcUserRepository implements UserRepository {

    @Autowired
    private DataSource dataSource;

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(AutoCloseable ... objs) {
        try {
            for(AutoCloseable obj : objs) {
                obj.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(User user) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?)", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(user.getId()));
            pstmt.setString(2, user.getUid());
            pstmt.setString(3, user.getName());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = String.format("SELECT * FROM %s", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("uid"), rs.getString("name")));
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return users;
    }

    @Override
    public User findByUid(String id) {
        String sql = String.format("SELECT * FROM %s WHERE ID=?", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if(rs.next()) user = new User(rs.getLong("id"), rs.getString("uid"), rs.getString("name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
        return user;
    }

    @Override
    public void update(User user) {
        String sql = String.format("UPDATE %s SET NAME=? WHERE ID=?", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, String.valueOf(user.getId()));
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
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
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(id));
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    @Override
    public void deleteByUid(String uid) {
        String sql = String.format("DELETE FROM %s WHERE UID=?", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uid);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt);
        }
    }
}