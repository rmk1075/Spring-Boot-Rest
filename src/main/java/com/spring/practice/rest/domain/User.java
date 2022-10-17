package com.spring.practice.rest.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;
    private String name;

    @Override
    public String toString() {
        return "User [id=" + id + ", uid=" + uid + ", name=" + name + "]";
    }

    // JPA Entity 에는 default constructor 가 필요하다.
    public User() {}

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }
    
    public String getUid() {
        return this.uid;
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
}
