package com.spring.practice.rest.domain.user.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserInfo {
    
    private Long id;
    private String uid;
    private String name;

    private Date created;
    private Date updated;
}
