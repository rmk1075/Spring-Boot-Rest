package com.spring.practice.rest.common;

import org.mapstruct.Mapper;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserInfo;

@Mapper
public interface CustomMapper {
    UserInfo userToUserInfo(User user);
    User userInfoToUser(UserInfo userInfo);
}
