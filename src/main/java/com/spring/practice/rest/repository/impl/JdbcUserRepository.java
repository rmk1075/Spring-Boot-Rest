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

import com.spring.practice.rest.domain.user.User;
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
    public User save(User user) {
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
        return user;
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
    public User findById(Long id) {
        String sql = String.format("SELECT * FROM %s WHERE ID=?", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
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
    public User findByUid(String uid) {
        String sql = String.format("SELECT * FROM %s WHERE UID=?", TABLE);
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, uid);
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
    public User update(User user) {
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

    @Override
    public User deleteById(Long id) {
        User user = this.findById(id);
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
        return user;
    }

    @Override
    public User deleteByUid(String uid) {
        User user = this.findByUid(uid);
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
        return user;
    }
}
