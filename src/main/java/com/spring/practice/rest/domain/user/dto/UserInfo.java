package com.spring.practice.rest.domain.user.dto;

import com.spring.practice.rest.domain.user.User;

import lombok.Data;

@Data
public class UserInfo {
    
    private Long id;
    private String uid;
    private String name;

    public static UserInfo ofUser(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUid(user.getUid());
        userInfo.setName(user.getName());
        return userInfo;
    }
}
