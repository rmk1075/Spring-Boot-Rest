package com.spring.practice.rest.domain.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserInfo {
    
    private Long id;
    private String uid;
    private String name;

    private Date created;
    private Date updated;
}
