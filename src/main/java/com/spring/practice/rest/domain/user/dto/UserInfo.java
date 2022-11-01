package com.spring.practice.rest.domain.user.dto;

import com.spring.practice.rest.domain.user.User;

public class UserInfo {
    
    // private Long id;
    private String uid;
    private String name;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserInfo ofUser(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(user.getUid());
        userInfo.setName(user.getName());
        return userInfo;
    }
}
