package com.spring.practice.rest.domain.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.spring.practice.rest.domain.user.dto.UserInfo;

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

    public User(Long id, String uid, String name) {
        this.id = id;
        this.uid = uid;
        this.name = name;
    }

    private void setId(Long id) {
        this.id = id;
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

    public static User ofUserInfo(UserInfo userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        user.setUid(userInfo.getUid());
        user.setName(userInfo.getName());
        return user;
    }
}
