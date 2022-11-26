package com.spring.practice.rest.service;

import java.util.List;

import com.spring.practice.rest.domain.user.dto.UserCreate;
import com.spring.practice.rest.domain.user.dto.UserInfo;
import com.spring.practice.rest.domain.user.dto.UserUpdate;

public interface UserService {
    public UserInfo getUser(Long id);
    public List<UserInfo> getUsers();
    public UserInfo createUser(UserCreate userCreate);
    public UserInfo updateUser(Long id, UserUpdate userUpdate);
    public UserInfo deleteUser(Long id);
}
