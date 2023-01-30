package com.spring.practice.rest.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserCreate {
    
    private String uid;
    private String name;
}
