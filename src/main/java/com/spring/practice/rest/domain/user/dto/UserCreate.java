package com.spring.practice.rest.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreate {
    
    private String uid;
    private String name;
}
