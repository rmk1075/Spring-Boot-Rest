package com.spring.practice.rest.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.practice.rest.domain.user.dto.UserInfo;

import lombok.Data;

@Data
@Entity
@Table(name = "USERS")
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private String name;

    // JPA Entity 에는 default constructor 가 필요하다.
    public User() {}

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public User(Long id, String uid, String name) {
        this.id = id;
        this.uid = uid;
        this.name = name;
    }
}
