package com.spring.practice.rest.common;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.spring.practice.rest.domain.user.User;
import com.spring.practice.rest.domain.user.dto.UserInfo;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonMapper {
    UserInfo userToUserInfo(User user);
    User userInfoToUser(UserInfo userInfo);
}
