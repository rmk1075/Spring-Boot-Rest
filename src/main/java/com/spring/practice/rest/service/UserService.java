package com.spring.practice.rest.service;

import java.util.List;

import com.spring.practice.rest.domain.user.dto.UserInfo;

public interface UserService {
    public UserInfo getUser(String uid);
    public List<UserInfo> getUsers();
    public UserInfo addUser(UserInfo user);
    public UserInfo updateUser(UserInfo user);
    public UserInfo removeUser(UserInfo user);
}
